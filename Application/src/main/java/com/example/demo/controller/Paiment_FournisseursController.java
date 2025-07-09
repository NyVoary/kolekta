package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Paiment_Fournisseurs;
import com.example.demo.service.FournisseursService;
import com.example.demo.service.Paiment_FournisseursService;

@Controller
public class Paiment_FournisseursController {

    @Autowired
    private Paiment_FournisseursService paiementService;

    @Autowired
    private FournisseursService fournisseurService;

    @GetMapping("/admin/paiement-fournisseurs")
    public String afficherPaiements(
        @RequestParam(required = false) String dateDebut,
        @RequestParam(required = false) String dateFin,
        @RequestParam(required = false) String nomFournisseur,
        Model model) {

        List<Paiment_Fournisseurs> paiements = paiementService.rechercherPaiements(
            dateDebut, dateFin, nomFournisseur);
        
        model.addAttribute("paiements", paiements);
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("nomFournisseur", nomFournisseur);
        
        return "admin/paiement-fournisseurs";
    }
}