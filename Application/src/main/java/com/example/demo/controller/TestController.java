package com.example.demo.controller;

import com.example.demo.service.TestService;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    private final ClientService clientService;

    public ClientService getService() {
        return clientService;
    }

    public TestController(ClientService service) {
        this.clientService = service;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        List<Client> clients = clientService.findAll();
        // boucler et faire un print du client
        System.out.println("Arak zany");
        for (Client client : clients) {
            System.out.println(client.getNom());
        }
        model.addAttribute("liste", clients);

        return "home";
    }
}
