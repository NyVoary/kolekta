package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;


@Controller
public class ClientController {

    private final ProduitsService produitsService;
    private final PanierService panierService;
    private final ClientService clientService;
    private final HistoriqueAchatService historiqueAchatService;

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);


    @Autowired
    public ClientController(ProduitsService produitsService, ClientService clientService,HistoriqueAchatService historiqueAchatService,PanierService panierService) {
        this.produitsService = produitsService;
        this.clientService = clientService;
        this.historiqueAchatService = historiqueAchatService;
        this.panierService = panierService;
    }

    @GetMapping("/login-client")
    public String login(Model model) {
        return "client/Login";
    }

    @GetMapping("/inscription-client")
    public String inscription(Model model) {
        return "client/Inscription";
    }

     @PostMapping("/inscription-client")
    public String inscriptionClient(@ModelAttribute ClientDTO clientDTO,
            Model model,
            HttpSession session) {
        try {
            Client client = clientService.inscrire(clientDTO);
            session.setAttribute("client-connected", client);
            panierService.creerPanierPourClient(client.getIdClient());
            model.addAttribute("message", "Inscription réussie !");
            return "redirect:/login-client"; // Page après succès
        } catch (Exception e) {
            session.setAttribute("error-client", e.getMessage());
            return "redirect:/inscription-client";
        }
    }

    @PostMapping("/login-client")
    public String loginClient(@ModelAttribute ClientLoginDTO loginDTO, HttpSession session) {
        try {
            Client client = clientService.login(loginDTO);
            session.setAttribute("client-connected", client);
            return "redirect:/";
            // return "redirect:/produits";
        } catch (Exception e) {
            session.setAttribute("error-client", e.getMessage());
            return "redirect:/login-client";
        }
    }

    @GetMapping("/")
    public String accueil(Model model) {
        return "redirect:/produits_liste";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "client/Login";
    }

    @GetMapping("monprofil")
    public String profil(){
        return "client/profil";
    }

    @GetMapping("/historique-achat")
    public String historiqueAchat(Model model,
            @RequestParam(value = "dateDebut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(value = "dateFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            HttpSession session) {
        try {
            Client client = (Client) session.getAttribute("client-connected");
            if (client == null) {
                model.addAttribute("error", "Vous devez être connecté pour voir l'historique d'achat.");
                return "client/HistoriqueAchat";
            }
            Long clientId = client.getIdClient();
            model.addAttribute("dateDebut", dateDebut);
            model.addAttribute("dateFin", dateFin);
            model.addAttribute("historique",
                    historiqueAchatService.getHistoriqueAchatByClientAndDate(clientId, dateDebut, dateFin));
            return "client/HistoriqueAchat";
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'historique d'achat", e);
            model.addAttribute("error", "Erreur lors de la récupération de l'historique d'achat");
            return "client/HistoriqueAchat";
        }
    }
}
