package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Produit;
import com.example.demo.service.ProduitService;

@Controller
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/produits")
    public String afficherProduits(Model model) {
        List<Produit> produits = produitService.getTousLesProduits();
        model.addAttribute("produits", produits);
        return "client/produits";
    }
}
