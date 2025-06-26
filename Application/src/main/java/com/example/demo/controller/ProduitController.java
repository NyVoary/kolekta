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

    @GetMapping("/produits/recherche")
    public String afficherProduitsFiltres(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            Model model) {

        List<Produit> produits = produitService.rechercherProduits(search, categorie, prixMin, prixMax);

        System.out.println("Nombre de produits filtrés trouvés : " + 
            (produits != null ? produits.size() : "null"));

        if (produits != null && !produits.isEmpty()) {
            for (Produit p : produits) {
                System.out.println("Produit : " + p.getNom() + 
                    " - Prix : " + p.getPrixVente());
            }
        }

        model.addAttribute("produits", produits);
        model.addAttribute("search", search);
        List<Categorie> categories = categorieService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("prixMin", prixMin);
        model.addAttribute("prixMax", prixMax);

        return "client/produits"; // même vue réutilisée
    }
}
