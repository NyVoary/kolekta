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
        System.out.println("=== Début de afficherProduits ===");

        List<Produit> produits = produitService.getTousLesProduits();

        System.out.println("Nombre de produits trouvés : " + 
            (produits != null ? produits.size() : "null"));

        if (produits != null && !produits.isEmpty()) {
            for (Produit p : produits) {
                System.out.println("Produit : " + p.getNom() + 
                    " - Prix : " + p.getPrixVente());
            }
        }

        model.addAttribute("produits", produits);

        System.out.println("=== Fin de afficherProduits ===");

        // Le fichier JSP se trouve dans /WEB-INF/jsp/client/produits.jsp
        return "client/produits";
    }
}
