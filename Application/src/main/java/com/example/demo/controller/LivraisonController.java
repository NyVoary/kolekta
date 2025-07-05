package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.*;
import com.example.demo.service.LivraisonService;
import com.example.demo.service.Partage;
import com.example.demo.repository.CommandeRepository;

@Controller
public class LivraisonController {
    @Autowired
    private LivraisonService livraisonService;

    @Autowired
    private Partage partage;

    @Autowired
    private CommandeRepository commandesRepository;
    // private Partage partage;

    @GetMapping("/Livraison-client")
    public String getLivraisons(Model model) {
        Long idCommande = partage.getIdCommande();

        if (idCommande == null) {
            // idCommande = (Long) session.getAttribute("idCommande");
            return "redirect:/panier";
        }

        Commandes commande = livraisonService.getCommande(idCommande);
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
            @RequestParam("dateLivraison") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLivraison,
            Model model) {

        Commandes commande = commandesRepository.findById(id).orElse(null);
        if (commande == null) {
            model.addAttribute("error", "Commande introuvable");
            return "client/panier";
        }

        // Update commande properties
        commande.setAdresseLivraison(adresseLivraison);
        commande.setVilleLivraison(villeLivraison);
        commande.setTelephoneLivraison(telephoneLivraison);
        commande.setEmailLivraison(emailLivraison);

        // Create livraison and set the commande relationship
        Livraison livraison = new Livraison();
        livraison.setDateLivraison(dateLivraison);
        livraison.setCommande(commande); // This line was missing!

        livraisonService.update(id, livraison, commande);

        model.addAttribute("message", "Livraison enregistrée avec succès !");
        return "client/panier";
    }

}
