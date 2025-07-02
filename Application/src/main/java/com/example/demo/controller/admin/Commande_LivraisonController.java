package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.LivraisonDTO;
import com.example.demo.service.LivraisonService;


@Controller
public class Commande_LivraisonController {

    @Autowired
    private LivraisonService livraisonService;

    @GetMapping("/admin/livraison")
    public String livraison(Model model) {
        List<LivraisonDTO> livraisons = livraisonService.getAllLivraisons();
        model.addAttribute("livraisons", livraisons);
        return "admin/commande-livraison/livraison";
    }

    @GetMapping("/admin/commande")
    public String commande(Model model) {
        return "admin/commande-livraison/commande";
    }

    @GetMapping("/admin/ajout-commande")
    public String ajoutCommande(Model model) {
        return "admin/commande-livraison/ajout-commande";
    }
}
