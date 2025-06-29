package com.example.demo.controller.admin;

import com.example.demo.entity.Acces;
import com.example.demo.service.AccesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private AccesService accesService;

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

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        return "admin/home";
    }

}
