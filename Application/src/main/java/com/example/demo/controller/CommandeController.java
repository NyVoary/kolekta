package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Panier;
import com.example.demo.entity.Panier_Produits;
import com.example.demo.entity.Produit;
import com.example.demo.entity.Utilisateur;
import com.example.demo.repository.PanierAddRepository;
import com.example.demo.repository.PanierProduitAddRepository;
import com.example.demo.repository.ProduitRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/commande")
public class CommandeController {

    private static final Logger logger = LoggerFactory.getLogger(CommandeController.class);

    @Autowired
    private PanierAddRepository panierAddRepository;

    @Autowired
    private PanierProduitAddRepository panierProduitAddRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String simulerConnexion(HttpSession session, RedirectAttributes redirectAttributes) {
        Utilisateur user = new Utilisateur();
        user.setId(2L); 
        user.setNom("TestClient2");
        user.setEmail("test2@example.com");

        // Vérifie si le client existe réellement
        Integer nbClients = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM clients WHERE id_client = ?",
                Integer.class,
                user.getId());

        if (nbClients == null || nbClients == 0) {
            logger.warn("Connexion simulée avec ID inexistant: {}", user.getId());
            redirectAttributes.addFlashAttribute("erreur", "Erreur : l'utilisateur ID " + user.getId() + " n'existe pas.");
            return "redirect:/commande/login-erreur";
        }

        session.setAttribute("utilisateur", user);
        logger.info("Utilisateur simulé connecté: {}", user.getId());
        return "redirect:/produits";
    }

    @GetMapping("/login-erreur")
    public String afficherLoginErreur() {
        // JSP login.jsp dans dossier client
        return "client/login-error";
    }

    @PostMapping("/ajouter")
    public String ajouterAuPanier(
            @RequestParam(required = false) Long produitId,
            @RequestParam(required = false) Integer quantite,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            logger.warn("Utilisateur non connecté – redirection vers login.jsp");
            redirectAttributes.addFlashAttribute("erreur", "Veuillez vous connecter avant d'ajouter au panier.");
            return "redirect:/commande/login-erreur";
        }

        if (produitId == null || quantite == null || quantite <= 0) {
            logger.warn("Paramètres invalides - produitId={}, quantite={}", produitId, quantite);
            redirectAttributes.addFlashAttribute("erreur", "Paramètres invalides.");
            return "redirect:/produits";
        }

        Long idClient = utilisateur.getId();

        try {
            Panier panier = panierAddRepository.findByIdClient(idClient)
                    .orElseGet(() -> {
                        Panier nouveau = new Panier();
                        nouveau.setIdClient(idClient);
                        return panierAddRepository.save(nouveau);
                    });

            Optional<Produit> produitOpt = produitRepository.findById(produitId);
            if (produitOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("erreur", "Produit introuvable.");
                return "redirect:/produits";
            }

            Panier_Produits ligne = new Panier_Produits();
            ligne.setIdPanier(panier.getId());
            ligne.setIdProduit(produitId);
            ligne.setQuantite(quantite);
            ligne.setPrixUnitaire(produitOpt.get().getPrixVente());

            panierProduitAddRepository.save(ligne);

            // Message de succès amélioré
            String nomProduit = produitOpt.get().getNom();
            redirectAttributes.addFlashAttribute("message", 
                "Produit ajouté dans le panier avec succès ! (" + quantite + " x " + nomProduit + ")");
            
            logger.info("Produit ajouté au panier - Client: {}, Produit: {}, Quantité: {}", 
                       idClient, produitId, quantite);
            
            return "redirect:/produits";

        } catch (Exception e) {
            logger.error("Erreur technique : ", e);
            redirectAttributes.addFlashAttribute("erreur", "Erreur technique : " + e.getMessage());
            return "redirect:/produits";
        }
    }
}