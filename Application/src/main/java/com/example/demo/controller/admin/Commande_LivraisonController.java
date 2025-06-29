package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Commande_LivraisonController {

    @GetMapping("/admin/livraison")
    public String livraison(Model model) {
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
