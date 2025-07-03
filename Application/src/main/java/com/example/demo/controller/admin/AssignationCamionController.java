package com.example.demo.controller.admin;

import com.example.demo.dto.AssignationCamionDTO;
import com.example.demo.entity.Camion;
import com.example.demo.service.AssignationCamionService;

import jakarta.servlet.http.HttpSession;

import com.example.demo.repository.CamionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
