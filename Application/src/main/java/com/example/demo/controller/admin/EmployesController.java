package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class EmployesController {
    
    @GetMapping("/ajout-employe")
    public String ajoutEmploye(Model model) {
        return "admin/employes/ajout";
    }

    @GetMapping("/liste-employe")
    public String listeEmploye(Model model) {
        return "admin/employes/liste";
    }

    @GetMapping("/paiement-employe")
    public String paiementEmploye(Model model) {
        return "admin/employes/paiement";
    }

    @GetMapping("/divers-employe")
    public String diversEmploye(Model model) {
        return "admin/employes/divers";
    }

    @GetMapping("/tache-employe")
    public String tacheEmploye(Model model) {
        return "admin/employes/tache";
    }

    @GetMapping("/stat-employe")
    public String statEmploye(Model model) {
        return "admin/employes/statistique";
    }
}
