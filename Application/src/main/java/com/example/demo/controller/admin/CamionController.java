package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// import com.example.demo.service.admin.CamionService;

@Controller
public class CamionController {
    @GetMapping("/admin/ajout-camion")
    public String ajoutCamion(Model model) {
        // model.addAttribute("camion", new Camion());
        return "admin/camions/ajout";
    }
    @GetMapping("/admin/liste-camion")
    public String listeCamion(Model model) {
        return "admin/camions/liste";
    }
    @GetMapping("/admin/paiement-camion")
    public String paiementCamion(Model model) {
        return "admin/camions/paiement";
    }
    @GetMapping("/admin/assignation-camion")
    public String assignationCamion(Model model) {
        return "admin/camions/assignation";
    }
}

