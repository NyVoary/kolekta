package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Produits;
import com.example.demo.service.CategoriesService;
import com.example.demo.service.ProduitsService;


@Controller
public class ProduitsController {

    private ProduitsService service;
    private CategoriesService categorieService;


    @Autowired
    public ProduitsController(ProduitsService produitsService,CategoriesService categorieService) {
        this.service = produitsService;
        this.categorieService = categorieService;
    }

    @GetMapping("/produits_saisonniers")
    public String listProduitsSaisonniers(Model model) {
        List<Produits> produits = service.listsProduitsSaisonniers();
        model.addAttribute("produits", produits);
        return "client/produits/produits_saison";
    }

    @RequestMapping("/produits_liste")
    public String afficherProduits(Model model) {
        List<Produits> produits = service.getAll();
        model.addAttribute("produits", produits);
        model.addAttribute("produitsSaison", service.listsProduitsSaisonniers());
        return "client/home";
    }

    
    @GetMapping("/recherche_produits")
    public String afficherProduits(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String categorie,
        @RequestParam(required = false) Double prixMin,
        @RequestParam(required = false) Double prixMax,
        Model model) {
            List<Produits> produits;

            List<Categories> categories = categorieService.getAll();
            
            if (search != null || categorie != null || prixMin != null || prixMax != null) {
                produits = service.rechercherProduits(search, categorie, prixMin, prixMax);
            } else {
                produits = service.getAll();
            }
            // Ajouter les produits et les catégories au modèle
            model.addAttribute("produits", produits);
            model.addAttribute("categories", categories);
            // Ajouter les paramètres de recherche pour pré-remplir le formulaire
            model.addAttribute("search", search);
            model.addAttribute("categorie", categorie);
            model.addAttribute("prixMin", prixMin);
            model.addAttribute("prixMax", prixMax);
            return "client/recherche-produits";
    }
}
