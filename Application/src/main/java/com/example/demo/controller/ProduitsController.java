package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Produits;
import com.example.demo.service.ProduitsService;


@Controller
public class ProduitsController {

    private ProduitsService service;

    @Autowired
    public ProduitsController(ProduitsService produitsService) {
        this.service = produitsService;
    }

    @GetMapping("/produits_saisonniers")
    public String listProduitsSaisonniers(Model model) {
        List<Produits> produits = service.listsProduitsSaisonniers();
        model.addAttribute("produits", produits);
        return "client/produits/produits_saison";
    }

    @RequestMapping("/produits")
    public String afficherProduits(Model model) {
        System.out.println("=== Début de afficherProduits ===");
        List<Produits> produits = service.getAll();
        System.out.println("Nombre de produits trouvés : " + 
            (produits != null ? produits.size() : "null"));
        model.addAttribute("produits", produits);
        model.addAttribute("produitsSaison", service.listsProduitsSaisonniers());
        System.out.println("=== Fin de afficherProduits ===");
        return "client/home";
    }
}
