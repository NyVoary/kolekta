package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Client_AdminContoller {
    @GetMapping("/admin/ajout-client")
    public String ajoutClient(Model model) {
        // model.addAttribute("client", new Client());
        return "admin/clients/ajout-client";
    }

    @GetMapping("/admin/liste-client")
    public String listeClient(Model model) {
        return "admin/clients/liste";
    }

    @GetMapping("/admin/stat-client")
    public String statClient(Model model) {
        return "admin/clients/statistique";
    }

}
