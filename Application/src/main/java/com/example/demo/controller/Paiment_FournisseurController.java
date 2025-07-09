package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Fournisseur;
import com.example.demo.entity.Paiment_Fournisseur;
import com.example.demo.service.FournisseursService;
import com.example.demo.service.Paiment_FournisseurService;

@Controller
public class Paiment_FournisseurController {

    @Autowired
    private Paiment_FournisseurService paiementService;

    @Autowired
    private FournisseursService fournisseurService;

    @GetMapping("/admin/paiement-fournisseurs")
    public String afficherPaiements(
        @RequestParam(required = false) String dateDebut,
        @RequestParam(required = false) String dateFin,
        @RequestParam(required = false) String nomFournisseur,
        Model model) {
            
            List<Paiment_Fournisseur> paiements;
            List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
            
            // Conversion des dates et gestion des paramètres nuls
            LocalDate debut = dateDebut != null && !dateDebut.isEmpty() ? LocalDate.parse(dateDebut) : null;
            LocalDate fin = dateFin != null && !dateFin.isEmpty() ? LocalDate.parse(dateFin) : null;
            String nom = nomFournisseur != null && nomFournisseur.isEmpty() ? null : nomFournisseur;

            // Vérifier si au moins un critère de recherche est fourni
            if (debut != null || fin != null || nom != null) {
                paiements = paiementService.rechercherPaiements(debut, fin, nom);

                // Logs pour débogage
                System.out.println("Nombre de paiements filtrés trouvés : " + (paiements != null ? paiements.size() : "null"));
                if (paiements != null && !paiements.isEmpty()) {
                    for (Paiment_Fournisseur p : paiements) {
                        System.out.println("Paiement : " + p.getId() + " - Montant : " + p.getMontant() + " - Fournisseur : " + 
                            (p.getFournisseur() != null ? p.getFournisseur().getNom() : "null"));
                    }
                }
            } else {
                paiements = paiementService.getTousLesPaiements();

                // Logs pour débogage
                System.out.println("Nombre de paiements trouvés : " + (paiements != null ? paiements.size() : "null"));
            }
            
            // Ajouter les paiements et les fournisseurs au modèle
            model.addAttribute("paiements", paiements);
            model.addAttribute("fournisseurs", fournisseurs);
            // Ajouter les paramètres de recherche pour pré-remplir le formulaire
            model.addAttribute("dateDebut", dateDebut);
            model.addAttribute("dateFin", dateFin);
            model.addAttribute("nomFournisseur", nomFournisseur);
            
            return "admin/paiement-fournisseurs";
    }
}