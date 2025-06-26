package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategorieController {
    @GetMapping("/admin/ajout-categorie")
    public String ajoutCategorie(Model model) {
        return "admin/categories/ajout-categorie";
    }

    @GetMapping("/admin/liste-categorie")
    public String listeCategorie(Model model) {
        return "admin/categories/liste";
    }
}
