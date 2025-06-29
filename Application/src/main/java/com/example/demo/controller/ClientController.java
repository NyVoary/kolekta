package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ClientLoginDTO;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;

import jakarta.servlet.http.HttpSession;

import com.example.demo.service.ProduitsService;

@Controller
public class ClientController {

    private final  ProduitsService produitsService;
    private final ClientService clientService;

    @Autowired
    public ClientController(ProduitsService produitsService, ClientService clientService) {
        this.produitsService = produitsService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String firstLogin(Model model) {
        return "client/Login";
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
        System.out.println("wawa");
        try {
            Client client = clientService.inscrire(clientDTO);
            session.setAttribute("client-connected", client);
            model.addAttribute("message", "Inscription réussie !");
            return "redirect:/"; // Page après succès
        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute("error-client", e.getMessage());
            return "redirect:/inscription-client";
        }
    }

    @PostMapping("/login-client")
    public String loginClient(@ModelAttribute ClientLoginDTO loginDTO, HttpSession session) {
        try {
            Client client = clientService.login(loginDTO);
            session.setAttribute("client-connected", client);
            return "redirect:/home";
        } catch (Exception e) {
            session.setAttribute("error-client", e.getMessage());
            return "redirect:/login-client";
        }
    }

    @GetMapping("/home")
    public String accueil(Model model) {
        model.addAttribute("produitsSaison", produitsService.listsProduitsSaisonniers());
        return "client/home";
    }
}