package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Categorie;
import com.example.demo.entity.Produit;
import com.example.demo.service.CategorieService;
import com.example.demo.service.ProduitService;

@Controller
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private CategorieService categorieService;

    @GetMapping("/produits")
    public String afficherProduits(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String categorie,
        @RequestParam(required = false) Double prixMin,
        @RequestParam(required = false) Double prixMax,
        Model model) {
            List<Produit> produits;
            
            // Vérifier si au moins un critère de recherche est fourni
            if (search != null || categorie != null || prixMin != null || prixMax != null) {
                produits = produitService.rechercherProduits(search, categorie, prixMin, prixMax);
            } else {
                produits = produitService.getTousLesProduits();
            }
            
            // Logs pour débogage
            System.out.println("Nombre de produits trouvés : " + (produits != null ? produits.size() : "null"));
            if (produits != null && !produits.isEmpty()) {
                for (Produit p : produits) {
                    System.out.println("Produit : " + p.getNom() + " - Prix : " + p.getPrixVente());
                }
            }
            // Ajouter les produits et les catégories au modèle
            model.addAttribute("produits", produits);
            List<Categorie> categories = categorieService.getAllCategories();
            model.addAttribute("categories", categories);
            // Ajouter les paramètres de recherche pour pré-remplir le formulaire
            model.addAttribute("search", search);
            model.addAttribute("categorie", categorie);
            model.addAttribute("prixMin", prixMin);
            model.addAttribute("prixMax", prixMax);
            
            return "client/produits";
    }
}   
