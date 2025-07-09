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
import com.example.demo.entity.AssignationsTaches;
import com.example.demo.entity.StatusTaches;
import com.example.demo.service.AccesService;
import com.example.demo.service.AssignationTacheService;
import com.example.demo.service.StatistiquesService;
import com.example.demo.service.StatusTachesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AccesService accesService;

    @Autowired
    private StatistiquesService statistiquesService;

    @Autowired
    private AssignationTacheService assignationTacheService;

    @Autowired
    private StatusTachesService statusTachesService;

    @GetMapping("/admin")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/admin/connexion")
    public String welcomePage(@RequestParam String username,
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
    //Modifier par Fenitra pour afficher les statistiques
    
    /*@GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        return "admin/home";
    }*/
    @GetMapping("/admin/dashboard")
    public String statProduct(Model model, HttpSession session) {
        try {
            Map<String, Object> stats = statistiquesService.getToutesLesStatistiques();
            model.addAttribute("statistics", stats);

            Acces acces = (Acces) session.getAttribute("acces");
            if (acces != null && acces.getIdEmploye() != null) {
                List<StatusTaches> status = statusTachesService.getAllStatusTaches();
                List<AssignationsTaches> taches = assignationTacheService.getTachesAujourdhuiParEmploye(Long.valueOf(acces.getIdEmploye()));
                System.out.println(Long.valueOf(acces.getIdEmploye()));
                model.addAttribute("taches", taches);
                model.addAttribute("status", status);
            }

            return "admin/home";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement des données: " + e.getMessage());
            return "admin/home";
        }
    }

}
