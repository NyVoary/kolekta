package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.EmployesDTO;
import com.example.demo.service.EmployesService;

@Controller
@RequestMapping("/admin")
public class EmployesController {

    private final EmployesService service;

    @Autowired
    public EmployesController(EmployesService service) {
        this.service = service;
    }

    @GetMapping("/ajout-employe")
    public String formulaire() {
        return "admin/employes/ajout";
    }
    
    @PostMapping("/ajout-employe")
    public String ajoutEmploye(@ModelAttribute EmployesDTO employesDTO, RedirectAttributes redirectAttributes) {
        try {
            service.CreatOrUpdate(employesDTO);
            redirectAttributes.addFlashAttribute("message", "Employes enregistré avec succes !");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/ajout-employe";
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
