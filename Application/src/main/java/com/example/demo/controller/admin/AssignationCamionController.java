package com.example.demo.controller.admin;

import com.example.demo.dto.AssignationCamionDTO;
import com.example.demo.entity.AssignationCamion;
import com.example.demo.entity.Camion;
import com.example.demo.service.AssignationCamionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.demo.repository.CamionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.demo.view.VCamionEtat;

@Controller
@RequestMapping("/admin/assignations")
public class AssignationCamionController {

    @Autowired
    private CamionRepository camionRepo;

    @Autowired
    private AssignationCamionService assignationService;

    @PostMapping("/ajout")
    public String ajouterAssignation(@ModelAttribute AssignationCamionDTO dto, HttpSession session) {
        try {
            assignationService.ajouterAssignation(dto); 
            session.setAttribute("succes-assignation", "Assignation du camion reussi"); 
        } catch (Exception e) {      
            e.printStackTrace(); // à remplacer par gestion d’erreur
            session.setAttribute("error-assignation", e.getMessage());
        }
        return "redirect:/admin/assignation-camion";
    } 

    @PostMapping("/finir-assignation")
public String finirAssignationPost(
        @RequestParam("arrivee") String dateArriveeStr,
        HttpServletRequest request,
        HttpSession session) {

    try {
        // Parsing de la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateArrivee = LocalDateTime.parse(dateArriveeStr, formatter);
        VCamionEtat camion = (VCamionEtat) session.getAttribute("camion-assigned");

        // Récupération de l'assignation à partir de l'ID
        AssignationCamion assignation = assignationService.findById(camion.getId_assignation_camion());

        // Mise à jour de la date d'arrivée
        assignation.setArrivee(dateArrivee);
        assignationService.save(assignation);

        session.setAttribute("succes-assignation", "La voiture a fini son assignation"); 
    } catch (Exception e) {
        session.setAttribute("error-assignation", "Erreur : " + e.getMessage()); 
    }

    return "redirect:/admin/liste-camion"; // rediriger vers la liste ou une autre page
}

}
