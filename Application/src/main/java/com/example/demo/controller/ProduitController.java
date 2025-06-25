package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProduitController {
    @GetMapping("/admin/ajout-produit")
     public String addProduct(Model model) {
        return "admin/produits/ajout-produit";
    }
    @GetMapping("/admin/liste-produit")
     public String listProduct(Model model) {
        return "admin/produits/liste-produit";
    }
    @GetMapping("/admin/stat-produit")
     public String statProduct(Model model) {
        return "admin/produits/statistique";
    }
}

