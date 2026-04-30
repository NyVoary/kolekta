package com.example.demo.controller;

import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import com.example.demo.service.HistoriqueAchatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HistoriqueAchatController {
    private final HistoriqueAchatService historiqueAchatService;
    private final ClientService clientService;

    public HistoriqueAchatController(HistoriqueAchatService historiqueAchatService, ClientService clientService) {
        this.historiqueAchatService = historiqueAchatService;
        this.clientService = clientService;
    }

    @GetMapping("/clients/{clientId}/historique-achats")
    public String afficherHistorique(@PathVariable Long clientId, Model model) {
        Client client = clientService.findById(clientId).orElse(null);
        if (client == null) {
            return "redirect:/error";
        }
        model.addAttribute("historiqueAchats", historiqueAchatService.getHistoriqueByClient(client));
        return "client/historique_achat";
    }
}
