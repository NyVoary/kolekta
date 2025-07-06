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
    private ProduitsRepository produitRepository;

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
            @RequestParam(required = false) Long produitId,
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
            return "redirect:/produits_liste";
        }

        Long idClient = utilisateur.getIdClient();

        try {
            Panier panier = panierAddRepository.findByIdClient(idClient)
                    .orElseGet(() -> {
                        Panier nouveau = new Panier();
                        nouveau.setIdClient(idClient);
                        return panierAddRepository.save(nouveau);
                    });

            Optional<Produits> produitOpt = produitRepository.findById(produitId);
            if (produitOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("erreur", "Produit introuvable.");
                return "redirect:/produits_liste";
            }

            PanierProduits ligne = new PanierProduits();
            ligne.setPanier(panier);
            ligne.setProduit(produitOpt.get());
            ligne.setQuantite(quantite.longValue());
            ligne.setPrixUnitaire(produitOpt.get().getPrixVente());

            panierProduitAddRepository.save(ligne);

            String nomProduit = produitOpt.get().getNom();
            redirectAttributes.addFlashAttribute("message",
                    "Produit ajouté dans le panier avec succès ! (" + quantite + " x " + nomProduit + ")");

            logger.info("Produit ajouté au panier - Client: {}, Produit: {}, Quantité: {}",
                    idClient, produitId, quantite);

            return "redirect:/produits_liste";

        } catch (Exception e) {
            logger.error("Erreur technique : ", e);
            redirectAttributes.addFlashAttribute("erreur", "Erreur technique : " + e.getMessage());
            return "redirect:/produits_liste";
        }
    }

    @GetMapping("/commandes/{id}")
    public String detailCommande(@PathVariable Long id, Model model, HttpSession session) {
        Commandes commande = commandeService.getCommandeWithProduits(id);
        model.addAttribute("commande", commande);
        model.addAttribute("progression", commande.getProgression());
        Client client = (Client) session.getAttribute("client-connected");
        if (client != null) {
            model.addAttribute("clientId", client.getIdClient());
        }
        return "client/detail-commande";
    }

    // @GetMapping("/historique")
    // public String historiqueCommandes(HttpSession session, Model model) {
    // Client utilisateur = (Client) session.getAttribute("client-connected");
    // Long idClient = Long.valueOf(utilisateur.getIdClient());
    // model.addAttribute("commandes",
    // commandeService.getCommandesByClientId(idClient));
    // return "client/historique-commandes";
    // }
}