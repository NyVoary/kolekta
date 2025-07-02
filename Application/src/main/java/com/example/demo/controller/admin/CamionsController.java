package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import com.example.demo.dto.CamionDTO;
import com.example.demo.dto.CamionDTO;
import com.example.demo.entity.Camion;
import com.example.demo.service.CamionService;
import com.example.demo.service.ProduitsService;

import jakarta.servlet.http.HttpSession;

// import com.example.demo.service.admin.CamionService;

@Controller
public class CamionsController {

    private final CamionService camionService; 

    @Autowired
    public CamionsController(CamionService camionService) {
        this.camionService = camionService;
    }

    @GetMapping("/admin/ajout-camion")
    public String ajoutCamion(Model model) {
        // model.addAttribute("camion", new Camion());
        return "admin/camions/ajout";
    }
    @GetMapping("/admin/liste-camion")
    public String listeCamion(Model model) {
        try {
            List<Camion> camions = camionService.getAll();
            model.addAttribute("camions", camions);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement des camions : " + e.getMessage());
        }
        // Redirection vers la vue
        return "admin/camions/liste";
    }

    @GetMapping("/admin/paiement-camion")
    public String paiementCamion(Model model) {
        return "admin/camions/paiement";
    }
    @GetMapping("/admin/assignation-camion")
    public String assignationCamion(Model model) throws Exception { 
        List<Camion> camions = camionService.getAll(); // ou repository.findAll() 
        model.addAttribute("camions", camions);
        return "admin/camions/assignation";
    }

    @PostMapping("/admin/ajout-camion")
    public String addCamionTraitement(@ModelAttribute CamionDTO camionDTO,
                                    Model model,
                                    HttpSession session) {
        System.out.println("wawa");
        try {
            camionService.ajouterCamion(camionDTO);
            session.setAttribute("succes-camion", "Ajout du camion reussi"); 
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error-camion", e.getMessage());
          
        } finally {
            return "redirect:/admin/ajout-camion";
        }
        
    }
}

