package com.example.demo.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import org.springframework.ui.Model;



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
    private ProduitsRepository produitsRepository;

    // @Autowired
    // private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommandeService commandeService;

    @GetMapping("/login-erreur")
    public String afficherLoginErreur() {
        return "client/login-client";
    }

    @PostMapping("/ajouter")
    public String ajouterAuPanier(
            @RequestParam(required = false) Integer produitId,
            @RequestParam(required = false) Integer quantite,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Client utilisateur = (Client) session.getAttribute("client-connected");

        if (utilisateur == null) {
            logger.warn("Utilisateur non connecté – redirection vers login.jsp");
            redirectAttributes.addFlashAttribute("erreur", "Veuillez vous connecter avant d'ajouter au panier.");
            return "redirect:/login-client";
        }

        if (produitId == null || quantite == null || quantite <= 0) {
            logger.warn("Paramètres invalides - produitId={}, quantite={}", produitId, quantite);
            redirectAttributes.addFlashAttribute("erreur", "Paramètres invalides.");
            return "redirect:/produits";
        }

        int idClient = utilisateur.getIdClient();

        try {
            Paniers panier = panierAddRepository.findByIdClient(idClient)
                    .orElseGet(() -> {
                        Paniers nouveau = new Paniers();
                        nouveau.setIdClient(idClient);
                        return panierAddRepository.save(nouveau);
                    });

            Optional<Produits> produitOpt = produitsRepository.findById(produitId);
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

    @GetMapping("/commandes/{id}")
    public String detailCommande(@PathVariable Long id, Model model) {
        Commande commande = commandeService.getCommandeWithProduits(id);
        model.addAttribute("commande", commande);
        model.addAttribute("progression", commande.getProgression());
        return "client/detail-commande";
    }
}