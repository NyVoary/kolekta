package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// import com.example.demo.service.admin.ProduitService;

@Controller
public class ProduitsController {
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

