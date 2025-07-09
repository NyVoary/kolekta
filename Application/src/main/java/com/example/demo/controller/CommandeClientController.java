package com.example.demo.controller;

import com.example.demo.dto.request.FilterCommandesRequest;
import com.example.demo.dto.response.CommandeClientResponse;
import com.example.demo.repository.StatusRepository;
import com.example.demo.service.CommandeService;
import com.example.demo.service.CommandeServiceInterface;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.*;

import java.util.List;

@Controller
public class CommandeClientController {

    @Autowired
    private CommandeServiceInterface commandeService;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/historiqueCommandes")
    // /historiqueCommandes
    public String listCommandes(
            @ModelAttribute FilterCommandesRequest filters,
            Model model, HttpSession session) {
        Clients client = (Clients) session.getAttribute("client-connected");
        if (client == null) {
            model.addAttribute("error", "Vous devez être connecté pour voir l'historique d'achat.");
            return "redirect:/login-client";
        }
        List<CommandeClientResponse> commandes = commandeService
                .getCommandesByClientWithFilters(client.getId(), filters);
        List<Status> allStatus = statusRepository.findAll();
        model.addAttribute("commandes", commandes);
        model.addAttribute("filters", filters);
        model.addAttribute("allStatus", allStatus);
        return "client/commandes";
    }
}