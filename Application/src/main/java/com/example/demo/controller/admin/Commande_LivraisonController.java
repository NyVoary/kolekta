package com.example.demo.controller.admin;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.dto.LivraisonDTO;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CommandeStatusRepository commandeStatusRepository;

    @GetMapping("/admin/validationCommande")
    public String validerCommande(
            @RequestParam("idCommande") Long idCommande,
            RedirectAttributes redirectAttributes) {
        // Récupération de la commande
        Commandes commande = commandeService.getCommandeById(idCommande);
        if (commande == null) {
            redirectAttributes.addFlashAttribute("error", "Commande introuvable");
            return "redirect:/admin/commande";
        }
        String statut = commandeService.getDernierStatutCommande(idCommande);
        if (!"En attente de validation".equalsIgnoreCase(statut)) {
            redirectAttributes.addFlashAttribute("error", "Cette commande ne peut pas être validée");
            return "redirect:/admin/commande";
        }
        Status status = statusRepository.findByLibelle("En cours de traitement");
        CommandeStatus commandeStatus = new CommandeStatus(commande, status, LocalDate.now());
        commandeStatusRepository.save(commandeStatus);
        status = statusRepository.findByLibelle("Validee");
        commandeStatus = new CommandeStatus(commande, status, LocalDate.now());
        commandeStatusRepository.save(commandeStatus);
        CommandePaiement commandePaiement = new CommandePaiement(
            commande, LocalDate.now(), commande.getMontantTotal(), "Mobile Money"
        );
        redirectAttributes.addFlashAttribute("success", "Commande validée avec succès");
        return "redirect:/admin/commande";
    }

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
        List<Clients> clients = clientService.getAllClients();
        List<Produits> produits = produitService.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("produits", produits);
        return "admin/commande-livraison/ajout-commande";
    }

    // @PostMapping("/admin/ajout-commande/traitement")
    // public String ajouterCommande(
    // @RequestParam("idClient") Long idClient,
    // @RequestParam("fraisLivraison") Double fraisLivraison,
    // @RequestParam("adresseLivraison") String adresseLivraison,
    // @RequestParam("villeLivraison") String villeLivraison,
    // @RequestParam("telephoneLivraison") String telephoneLivraison,
    // @RequestParam(value = "emailLivraison", required = false) String
    // emailLivraison,
    // @RequestParam("produitIds") List<Long> produitIds,
    // @RequestParam("quantites") List<Integer> quantites) {
    // commandeService.ajouterCommande(idClient, fraisLivraison, adresseLivraison,
    // villeLivraison,
    // telephoneLivraison, emailLivraison, produitIds, quantites);
    // return "redirect:/admin/commande";
    // }

    @PostMapping("/admin/ajout-commande/traitement")
    public String ajouterCommande(
            @RequestParam("idClient") Long idClient,
            @RequestParam("fraisLivraison") Double fraisLivraison,
            @RequestParam("adresseLivraison") String adresseLivraison,
            @RequestParam("villeLivraison") String villeLivraison,
            @RequestParam("telephoneLivraison") String telephoneLivraison,
            @RequestParam(value = "emailLivraison", required = false) String emailLivraison,
            @RequestParam("produitIds[]") List<Long> produitIds,
            @RequestParam("quantites[]") List<Integer> quantites) {

        commandeService.ajouterCommande(idClient, fraisLivraison, adresseLivraison,
                villeLivraison, telephoneLivraison,
                emailLivraison, produitIds, quantites);

        return "redirect:/admin/commande";
    }

}