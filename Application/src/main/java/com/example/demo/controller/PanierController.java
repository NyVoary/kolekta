package com.example.demo.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.*;
import com.example.demo.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/panier")
public class PanierController {

    @Autowired
    private PanierService panierService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private Partage partage;

    // idClient fixé ici
    private Long idClient = 1L;

    @GetMapping("/monpanier")
    public String afficherPanier(Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        if (session.getAttribute("client-connected") == null) {
            return "redirect:/login-client";
        }
        Client client = (Client) session.getAttribute("client-connected");
        idClient = client.getIdClient();
        Optional<Panier> panierOpt = panierService.getPanierAvecProduits(idClient);

        if (panierOpt.isPresent()) {
            Panier panier = panierOpt.get();
            Double montantHT = panierService.calculerMontantHT(panier);
            Double tva = panierService.calculerTVA(montantHT);
            Double montantTTC = panierService.calculerMontantTTC(montantHT);
            Double fraisLivraison = panierService.getFraisLivraison(montantHT);
            Double montantTotal = panierService.calculerMontantTotal(montantHT);

            model.addAttribute("panier", panier);
            model.addAttribute("montantHT", montantHT);
            model.addAttribute("tva", tva);
            model.addAttribute("montantTTC", montantTTC);
            model.addAttribute("fraisLivraison", fraisLivraison);
            model.addAttribute("montantTotal", montantTotal);
            model.addAttribute("nombreArticles", panier.getPanierProduits().size());
        } else {
            panierService.creerPanierPourClient(idClient);
            model.addAttribute("panier", null);
            model.addAttribute("montantHT", 0.0);
            model.addAttribute("tva", 0.0);
            model.addAttribute("montantTTC", 0.0);
            model.addAttribute("fraisLivraison", panierService.getFraisLivraison(0.0));
            model.addAttribute("montantTotal", panierService.getFraisLivraison(0.0));
            model.addAttribute("nombreArticles", 0);
        }

        return "client/panier";
    }

    @PostMapping("/ajouter")
    public String ajouterProduit(Long idProduit, Integer quantite, RedirectAttributes redirectAttributes) {
        if (quantite == null)
            quantite = 1;

        try {
            panierService.ajouterProduitAuPanier(idClient, idProduit, quantite);
            redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté au panier avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de l'ajout du produit : " + e.getMessage());
        }

        return "redirect:/panier";
    }

    @PostMapping("/modifier-quantite")
    public String modifierQuantite(Long idProduit, Integer quantite, RedirectAttributes redirectAttrs) {
        panierService.mettreAJourQuantite(idClient, idProduit, quantite);
        redirectAttrs.addFlashAttribute("successMessage", "Quantité mise à jour.");
        return "redirect:/panier/monpanier";
    }

    @PostMapping("/supprimer")
    public String supprimerProduit(Long idProduit, RedirectAttributes redirectAttrs) {
        panierService.supprimerProduitDuPanier(idClient, idProduit);
        redirectAttrs.addFlashAttribute("successMessage", "Produit supprimé du panier.");
        return "redirect:/panier/monpanier";
    }

    @PostMapping("/vider")
    public String viderPanier(RedirectAttributes redirectAttrs) {
        panierService.viderPanier(idClient);
        redirectAttrs.addFlashAttribute("successMessage", "Le panier a été vidé.");
        return "redirect:/panier/monpanier";
    }

    @PostMapping("/valider")
    public String validerAchat(RedirectAttributes redirectAttributes) {
        if (panierService.compterArticlesPanier(idClient) == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Votre panier est vide !");
            return "redirect:/panier/monpanier";
        }

        Commandes commandeCree = commandeService.creerCommandeDepuisPanier(idClient);
        redirectAttributes.addFlashAttribute("successMessage", "Commande enregistrée avec succès !");

        System.out.println(
                "idCommande-------------------------------------------------------------:" + commandeCree.getId());
        partage.setIdCommande(commandeCree.getId());

        return "redirect:/Livraison-client";
    }
}
