package com.example.demo.controller;

import com.example.demo.entity.Disponibilite;
import com.example.demo.entity.Fournisseurs;
import com.example.demo.entity.Produits;
import com.example.demo.repository.DisponibiliteRepository;
import com.example.demo.service.FournisseursService;
import com.example.demo.service.ProduitsService;
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
    @Autowired
    private ProduitsService produitsService;
    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

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

    @GetMapping("/admin/fournisseurs/edit")
    public String editFournisseurForm(Long id, Model model) {
        Fournisseurs fournisseur = fournisseursService.findById(id);
        model.addAttribute("fournisseur", fournisseur);
        return "admin/fournisseurs/edit";
    }

    @PostMapping("/admin/fournisseurs/update")
    public String updateFournisseur(@ModelAttribute("fournisseur") Fournisseurs fournisseur) {
        fournisseursService.save(fournisseur);
        return "redirect:/admin/fournisseurs/liste";
    }

    @GetMapping("/admin/fournisseurs/delete")
    public String deleteFournisseur(Long id) {
        fournisseursService.deleteById(id);
        return "redirect:/admin/fournisseurs/liste";
    }

    @GetMapping("/admin/fournisseurs/ajout-disponibilite")
    public String showAjoutDisponibiliteForm(Model model) {
        model.addAttribute("disponibilite", new Disponibilite());
        model.addAttribute("fournisseurs", fournisseursService.findAll());
        model.addAttribute("produits", produitsService.getAll());
        return "admin/fournisseurs/ajout-disponibilite";
    }

    @PostMapping("/admin/fournisseurs/ajout-disponibilite")
    public String addDisponibilite(@ModelAttribute("disponibilite") Disponibilite disponibilite, Model model) {
        disponibiliteRepository.save(disponibilite);
        model.addAttribute("success", "Disponibilité ajoutée avec succès !");
        return "redirect:/admin/fournisseurs/ajout-disponibilite";
    }

    @GetMapping("/admin/fournisseurs/statistique")
    public String statistiquesFournisseurs(Model model) {
        List<Fournisseurs> fournisseurs = fournisseursService.findAll();
        int totalFournisseurs = fournisseurs.size();
        int totalProduits = 0;
        String topFournisseurNom = "-";
        int maxProduits = 0;
        int nbProduitsTop = 0;
        // Pour le graphique
        List<Map<String, Object>> fournisseursList = new java.util.ArrayList<>();
        for (Fournisseurs f : fournisseurs) {
            List<Produits> produits = fournisseursService.getProduitsByFournisseur(f.getId());
            int nbProduits = produits.size();
            totalProduits += nbProduits;
            if (nbProduits > maxProduits) {
                maxProduits = nbProduits;
                topFournisseurNom = f.getNom();
                nbProduitsTop = nbProduits;
            }
            Map<String, Object> entry = new java.util.HashMap<>();
            entry.put("nom", f.getNom());
            entry.put("nbProduits", nbProduits);
            fournisseursList.add(entry);
        }
        model.addAttribute("totalFournisseurs", totalFournisseurs);
        model.addAttribute("totalProduits", totalProduits);
        Map<String, Object> topFournisseur = new java.util.HashMap<>();
        topFournisseur.put("nom", topFournisseurNom);
        topFournisseur.put("nbProduits", nbProduitsTop);
        model.addAttribute("topFournisseur", topFournisseur);
        // Générer JSON pour le graphique
        String fournisseursJson = "[]";
        try {
            fournisseursJson = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(fournisseursList);
        } catch (Exception e) {
            // fallback
        }
        model.addAttribute("fournisseursJson", fournisseursJson);
        return "admin/fournisseurs/statistique";
    }
}
