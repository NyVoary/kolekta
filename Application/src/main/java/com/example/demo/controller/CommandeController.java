package com.example.demo.controller;

import com.example.demo.entity.Commande;
import com.example.demo.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/commandes")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @GetMapping("/{id}")
    public String detailCommande(@PathVariable Long id, Model model) {
        Commande commande = commandeService.getCommandeWithProduits(id);
        model.addAttribute("commande", commande);
        model.addAttribute("progression", commande.getProgression());
        return "client/detail-commande";
    }
}