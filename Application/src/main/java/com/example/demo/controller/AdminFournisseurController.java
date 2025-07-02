package com.example.demo.controller;

import com.example.demo.entity.Fournisseurs;
import com.example.demo.entity.Produits;
import com.example.demo.service.FournisseursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminFournisseurController {
    @Autowired
    private FournisseursService fournisseursService;

    @GetMapping("/admin/fournisseurs/ajout")
    public String showAjoutFournisseurForm(Model model) {
        model.addAttribute("fournisseur", new Fournisseurs());
        return "admin/fournisseurs/ajout";
    }

    @PostMapping("/admin/fournisseurs/ajout")
    public String addFournisseur(@ModelAttribute("fournisseur") Fournisseurs fournisseur, Model model) {
        fournisseursService.save(fournisseur);
        model.addAttribute("success", "Fournisseur ajouté avec succès !");
        model.addAttribute("fournisseur", new Fournisseurs());
        return "admin/fournisseurs/ajout";
    }

    @GetMapping("/admin/fournisseurs/liste")
    public String listeFournisseurs(Model model) {
        List<Fournisseurs> fournisseurs = fournisseursService.findAll();
        Map<Long, List<Produits>> produitsParFournisseur = new HashMap<>();
        for (Fournisseurs f : fournisseurs) {
            List<Produits> produits = fournisseursService.getProduitsByFournisseur(f.getId());
            produitsParFournisseur.put(f.getId(), produits);
        }
        model.addAttribute("fournisseurs", fournisseurs);
        model.addAttribute("produitsParFournisseur", produitsParFournisseur);
        return "admin/fournisseurs/liste";
    }
}
