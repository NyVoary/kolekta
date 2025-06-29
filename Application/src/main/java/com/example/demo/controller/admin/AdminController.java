package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String login(Model model) {
        return "admin/login";
    }

    @PostMapping("/admin/connexion")
    public String welcomePage(@RequestParam String username, @RequestParam String password, Model model) {
        // Traitement (à remplacer par ta logique réelle)
        model.addAttribute("username", username);
        return "admin/home";
    }

}
