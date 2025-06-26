package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FournisseurController {
    
    @GetMapping("/admin/ajout-fournisseur")
    public String ajoutFournisseur(Model model) {
        // model.addAttribute("client", new Client());
        return "admin/fournisseurs/ajout";
    }

    @GetMapping("/admin/liste-fournisseur")
    public String listeFournisseur(Model model) {
        return "admin/fournisseurs/liste";
    }

    @GetMapping("/admin/paiement-fournisseur")
    public String paiementFournisseur(Model model) {
        return "admin/fournisseurs/paiement";
    }

    @GetMapping("/admin/stat-fournisseur")
    public String statFournisseur(Model model) {
        return "admin/fournisseurs/statistique";
    }

    @GetMapping("/admin/ajout-disponibilite")
    public String ajoutDisponibilite(Model model) {
        return "admin/fournisseurs/ajout-disponibilite";
    }

}
