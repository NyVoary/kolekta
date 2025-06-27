package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Panier;
import com.example.demo.entity.Commande;
import com.example.demo.service.PanierService;
import com.example.demo.service.CommandeService;
import com.example.demo.service.Partage;

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
    private final Long idClient = 1L;

    @GetMapping
    public String afficherPanier(Model model, RedirectAttributes redirectAttributes) {

        Optional<Panier> panierOpt = panierService.getPanierAvecProduits(idClient);

        if (panierOpt.isPresent()) {
            Panier panier = panierOpt.get();
            BigDecimal montantHT = panierService.calculerMontantHT(panier);
            BigDecimal tva = panierService.calculerTVA(montantHT);
            BigDecimal montantTTC = panierService.calculerMontantTTC(montantHT);
            BigDecimal fraisLivraison = panierService.getFraisLivraison();
            BigDecimal montantTotal = panierService.calculerMontantTotal(montantTTC);

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
            model.addAttribute("montantHT", BigDecimal.ZERO);
            model.addAttribute("tva", BigDecimal.ZERO);
            model.addAttribute("montantTTC", BigDecimal.ZERO);
            model.addAttribute("fraisLivraison", panierService.getFraisLivraison());
            model.addAttribute("montantTotal", panierService.getFraisLivraison());
            model.addAttribute("nombreArticles", 0);
        }

        return "client/panier";
    }

    @PostMapping("/ajouter")
    public String ajouterProduit(Long idProduit, Integer quantite, RedirectAttributes redirectAttributes) {
        if (quantite == null) quantite = 1;

        try {
            panierService.ajouterProduitAuPanier(idClient, idProduit, quantite);
            redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté au panier avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout du produit : " + e.getMessage());
        }

        return "redirect:/panier";
    }

    @PostMapping("/modifier-quantite")
    public String modifierQuantite(Long idProduit, Integer quantite, RedirectAttributes redirectAttrs) {
        panierService.mettreAJourQuantite(idClient, idProduit, quantite);
        redirectAttrs.addFlashAttribute("successMessage", "Quantité mise à jour.");
        return "redirect:/panier";
    }

    @PostMapping("/supprimer")
    public String supprimerProduit(Long idProduit, RedirectAttributes redirectAttrs) {
        panierService.supprimerProduitDuPanier(idClient, idProduit);
        redirectAttrs.addFlashAttribute("successMessage", "Produit supprimé du panier.");
        return "redirect:/panier";
    }

    @PostMapping("/vider")
    public String viderPanier(RedirectAttributes redirectAttrs) {
        panierService.viderPanier(idClient);
        redirectAttrs.addFlashAttribute("successMessage", "Le panier a été vidé.");
        return "redirect:/panier";
    }

    @PostMapping("/valider")
    public String validerAchat(RedirectAttributes redirectAttributes) {
        if (panierService.compterArticlesPanier(idClient) == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Votre panier est vide !");
            return "redirect:/panier";
        }

        Commande commandeCree = commandeService.creerCommandeDepuisPanier(idClient);
        redirectAttributes.addFlashAttribute("successMessage", "Commande enregistrée avec succès !");
        System.out.println("idCommande-------------------------------------------------------------:"+commandeCree.getIdCommande());
        partage.setIdCommande(commandeCree.getIdCommande());
        return "redirect:/Livraison-client";
    }
}
