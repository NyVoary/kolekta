package com.example.demo.controller.admin;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.dto.LivraisonDTO;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Commande_LivraisonController {

    @Autowired
    private LivraisonService livraisonService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProduitsService produitService;

    @GetMapping("/admin/livraison")
    public String livraison(Model model) {
        List<LivraisonDTO> livraisons = livraisonService.getAllLivraisons();
        model.addAttribute("livraisons", livraisons);
        return "admin/commande-livraison/livraison";
    }

    @GetMapping("/admin/commande")
    public String commande(Model model) {
        List<CommandeDTO> commandes = commandeService.getAllCommandes();
        model.addAttribute("commandes", commandes);
        return "admin/commande-livraison/commande";
    }

    @GetMapping("/admin/ajout-commande")
    public String showAjouterCommandeForm(Model model) {
        List<Client> clients = clientService.getAllClients();
        List<Produits> produits = produitService.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("produits", produits);
        return "admin/commande-livraison/ajout-commande";
    }

    @PostMapping("/admin/ajout-commande/traitement")
    public String ajouterCommande(
            @RequestParam("idClient") Long idClient,
            @RequestParam("fraisLivraison") Double fraisLivraison,
            @RequestParam("adresseLivraison") String adresseLivraison,
            @RequestParam("villeLivraison") String villeLivraison,
            @RequestParam("telephoneLivraison") String telephoneLivraison,
            @RequestParam(value = "emailLivraison", required = false) String emailLivraison,
            @RequestParam("produitIds") List<Long> produitIds,
            @RequestParam("quantites") List<Integer> quantites) {
            commandeService.ajouterCommande(idClient, fraisLivraison, adresseLivraison, villeLivraison,
                telephoneLivraison, emailLivraison, produitIds, quantites);
        return "redirect:/admin/commande";
    }
}