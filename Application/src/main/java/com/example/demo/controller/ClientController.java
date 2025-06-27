package com.example.demo.controller;

import com.example.demo.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.AchatService;
import com.example.demo.service.ClientService;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClientController {
    private final AchatService achatService;
    private final ClientService clientService;

    public ClientController(AchatService achatService, ClientService clientService) {
        this.achatService = achatService;
        this.clientService = clientService;
    }

    @GetMapping("/login-client")
    public String welcomePage(Model model) {
        return "client/Login";
    }
    @GetMapping("/clients/{clientId}/achats")
    public String historiqueAchats(@PathVariable Long clientId, Model model) {
        Client client = clientService.findById(clientId).orElse(null);
        if (client == null) {
            return "redirect:/error";
        }
        model.addAttribute("achats", achatService.getAchatsByClient(client));
        return "client/historique_achat";
    }
}
