package com.example.demo.controller;

import com.example.demo.entity.Disponibilite;
import com.example.demo.entity.Fournisseurs;
import com.example.demo.entity.Produits;
import com.example.demo.service.DisponibiliteService;
import com.example.demo.service.FournisseursService;
import com.example.demo.service.ProduitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DisponibiliteController {
    @Autowired
    private DisponibiliteService disponibiliteService;
    @Autowired
    private FournisseursService fournisseursService;
    @Autowired
    private ProduitsService produitsService;

    @GetMapping("/admin/disponibilite/ajout")
    public String showAjoutDisponibiliteForm(Model model) {
        model.addAttribute("disponibilite", new Disponibilite());
        List<Fournisseurs> fournisseurs = fournisseursService.findAll();
        List<Produits> produits = produitsService.getAll();
        model.addAttribute("fournisseurs", fournisseurs);
        model.addAttribute("produits", produits);
        return "admin/disponibilite/ajout";
    }

    @PostMapping("/admin/disponibilite/ajout")
    public String addDisponibilite(@ModelAttribute("disponibilite") Disponibilite disponibilite, Model model) {
        disponibiliteService.save(disponibilite);
        model.addAttribute("success", "Disponibilité ajoutée avec succès !");
        return "redirect:/admin/disponibilite/ajout";
    }
}
