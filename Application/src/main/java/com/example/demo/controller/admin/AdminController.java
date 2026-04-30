package com.example.demo.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Acces;
import com.example.demo.entity.AssignationTache;
import com.example.demo.repository.AssignationTacheRepository;
import com.example.demo.service.AccesService;
import com.example.demo.service.StatistiquesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AccesService accesService;

    @Autowired
    private AssignationTacheRepository assignationTacheRepository;

    @Autowired
    private StatistiquesService statistiquesService;

    @GetMapping("/admin")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/admin/connexion")
    public String welcomePage(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        String role = accesService.verifierIdentifiants(username, password);
        Acces acces = accesService.getAccesByUsername(username);

        if (role != null) {
            session.setAttribute("acces", acces);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("erreur", "Identifiants incorrects ou accès refusé");
            return "admin/login";
        }
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin";
    }

    @GetMapping("/admin/dashboard")
    public String statProduct(Model model, HttpSession session) {
        try {
            Acces acc = (Acces) session.getAttribute("acces");
            if (acc == null || acc.getEmployes() == null) {
                model.addAttribute("error", "Session expirée ou accès invalide.");
                return "admin/login";
            }

            Map<String, Object> stats = statistiquesService.getToutesLesStatistiques();
            List<AssignationTache> taches = assignationTacheRepository.findByEmployes(acc.getEmployes());

            model.addAttribute("statistics", stats);
            model.addAttribute("taches", taches);
            model.addAttribute("assignationTacheStatuses", 
    new String[] {"En attente", "En cours", "Terminée", "Annulée"});
            // Ajoute aussi la liste des statuts pour la modal
            model.addAttribute("status", AssignationTache.theStatus);
            return "admin/home";

        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement des statistiques : " + e.getMessage());
            return "admin/home";
        }
    }

}
