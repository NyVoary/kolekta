package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Commande;
import com.example.demo.entity.Livraison;
import com.example.demo.service.LivraisonService;
import com.example.demo.service.Partage;

@Controller
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;

    @Autowired
    private Partage partage;

    @GetMapping("/Livraison-client")
    public String getLivraisons(Model model) {
        // Long id ito mandray anle 1 commande creer apres validation panier exemple io 1 io
        Long id = 1L; 
        Commande commande = livraisonService.getCommande(id);
        model.addAttribute("liste", commande);
        return "client/Livraison";
    }

    @PostMapping("/Livraison-client/save")
    public String save(
        @RequestParam("idCommande") Long id,
        @RequestParam("adresseLivraison") String adresseLivraison,
        @RequestParam("villeLivraison") String villeLivraison,
        @RequestParam("telephoneLivraison") String telephoneLivraison,
        @RequestParam("emailLivraison") String emailLivraison,
        @RequestParam("dateLivraison") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLivraison
    ) {
        Commande commande = new Commande();
        commande.setAdresseLivraison(adresseLivraison);
        commande.setVilleLivraison(villeLivraison);
        commande.setTelephoneLivraison(telephoneLivraison);
        commande.setEmailLivraison(emailLivraison);

        Livraison livraison = new Livraison();
        livraison.setDateLivraison(dateLivraison);

        livraisonService.update(id, livraison, commande);

        return "redirect:/Livraison-client"; 
    }
}
