package com.example.demo.controller.admin;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Acces;
import com.example.demo.entity.AssignationTache;
import com.example.demo.entity.Employe;
import com.example.demo.service.AccesService;
import com.example.demo.service.EmployeService;
import com.example.demo.service.StatistiquesService;
import com.example.demo.service.TacheService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final AccesService accesService;
    private final StatistiquesService statistiquesService;
    private final TacheService tacheService;
    private final EmployeService employeService;

    public AdminController(AccesService accesService,
                         StatistiquesService statistiquesService,
                         TacheService tacheService,
                         EmployeService employeService) {
        this.accesService = accesService;
        this.statistiquesService = statistiquesService;
        this.tacheService = tacheService;
        this.employeService = employeService;
    }

    @GetMapping({"/admin", "/admin/"})
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
                Employe employe = employeService.getEmployeById(acces.getIdEmploye());
                if (employe != null) {
                    List<AssignationTache> taches = tacheService.getTachesParEmploye(employe);
                    model.addAttribute("taches", taches);
                    model.addAttribute("nomEmploye", employe.getNomComplet());
                    System.out.println("Nombre de tâches trouvées: " + taches.size());
                    taches.forEach(t -> System.out.println(t.getTitre()));
                }
            } else {
                model.addAttribute("taches", Collections.emptyList());
            }
            return "admin/home";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement des données: " + e.getMessage());
            e.printStackTrace();
            return "admin/home";
        }
    }
}
