package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Produits;
import com.example.demo.service.CategoriesService;
import com.example.demo.service.ProduitsService;

@Controller
public class ProduitController {

    @Autowired
    private ProduitsService produitService;

    @Autowired
    private CategoriesService categorieService;

    @GetMapping("/produits")
    public String afficherProduits(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String categorie,
        @RequestParam(required = false) Double prixMin,
        @RequestParam(required = false) Double prixMax,
        Model model) {
            List<Produits> produits;

            List<Categories> categories = categorieService.getAllCategories();
            
            // Vérifier si au moins un critère de recherche est fourni
            if (search != null || categorie != null || prixMin != null || prixMax != null) {
                produits = produitService.rechercherProduits(search, categorie, prixMin, prixMax);

                // Logs pour débogage
                System.out.println("Nombre de produits filtrés trouvés : " + (produits != null ? produits.size() : "null"));
                if (produits != null && !produits.isEmpty()) {
                    for (Produits p : produits) {
                        System.out.println("Produit : " + p.getNom() + " - Prix : " + p.getPrixVente());
                    }
                }
            } else {
                produits = produitService.getAll();

                // Logs pour débogage
                System.out.println("Nombre de produits trouvés : " + (produits != null ? produits.size() : "null"));
                if (produits != null && !produits.isEmpty()) {
                    for (Produits p : produits) {
                        System.out.println("Produit : " + p.getNom() + " - Prix : " + p.getPrixVente());
                    }
                }
            }
            
            // Ajouter les produits et les catégories au modèle
            model.addAttribute("produits", produits);
            model.addAttribute("categories", categories);
            // Ajouter les paramètres de recherche pour pré-remplir le formulaire
            model.addAttribute("search", search);
            model.addAttribute("categorie", categorie);
            model.addAttribute("prixMin", prixMin);
            model.addAttribute("prixMax", prixMax);
            
            return "client/produits";
    }
}   
