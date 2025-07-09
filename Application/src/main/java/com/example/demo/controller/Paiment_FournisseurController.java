package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        List<Paiment_Fournisseur> paiements = paiementService.rechercherPaiements(
            dateDebut, dateFin, nomFournisseur);
        
        model.addAttribute("paiements", paiements);
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("nomFournisseur", nomFournisseur);
        
        return "admin/paiement-fournisseurs";
    }
}