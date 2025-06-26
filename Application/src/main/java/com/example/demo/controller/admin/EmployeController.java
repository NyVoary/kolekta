package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeController {
    @GetMapping("/admin/ajout-employe")
    public String ajoutEmploye(Model model) {
        return "admin/employes/ajout";
    }
    @GetMapping("/admin/liste-employe")
    public String listeEmploye(Model model) {
        return "admin/employes/liste";
    }
    @GetMapping("/admin/paiement-employe")
    public String paiementEmploye(Model model) {
        return "admin/employes/paiement";
    }
    @GetMapping("/admin/divers-employe")
    public String diversEmploye(Model model) {
        return "admin/employes/divers";
    }
    @GetMapping("/admin/tache-employe")
    public String tacheEmploye(Model model) {
        return "admin/employes/tache";
    }
    @GetMapping("/admin/stat-employe")
    public String statEmploye(Model model) {    
        return "admin/employes/statistique";
    }
}
