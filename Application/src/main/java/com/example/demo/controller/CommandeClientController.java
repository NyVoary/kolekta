package com.example.demo.controller;

import com.example.demo.dto.request.FilterCommandesRequest;
import com.example.demo.dto.response.CommandeClientResponse;
import com.example.demo.entity.Status;
import com.example.demo.repository.StatusRepository;
import com.example.demo.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class CommandeClientController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/{clientId}/commandes")
    public String listCommandes(
            @PathVariable("clientId") Long clientId,
            @ModelAttribute FilterCommandesRequest filters,
            Model model) {
        
        List<CommandeClientResponse> commandes = commandeService
            .getCommandesByClientWithFilters(clientId, filters);
        List<Status> allStatus = statusRepository.findAll();
        
        model.addAttribute("commandes", commandes);
        model.addAttribute("filters", filters);
        model.addAttribute("clientId", clientId);
        model.addAttribute("allStatus", allStatus);
        
        return "client/commandes";
    }
}