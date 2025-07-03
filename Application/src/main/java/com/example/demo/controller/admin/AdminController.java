package com.example.demo.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Acces;
import com.example.demo.service.AccesService;
import com.example.demo.service.StatistiquesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AccesService accesService;

    @Autowired
    private StatistiquesService statistiquesService;

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
            return "admin/home";
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

    /*@GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        return "admin/home";
    }*/
    @GetMapping("/admin/dashboard")
     public String statProduct(Model model) {
        try {
            Map<String, Object> stats = statistiquesService.getToutesLesStatistiques();
            model.addAttribute("statistics", stats);
            return "admin/home";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement des statistiques: " + e.getMessage());
            return "admin/home";
        }
    }

}
