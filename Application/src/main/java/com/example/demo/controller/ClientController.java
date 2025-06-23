package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {
    @GetMapping("/login-client")
    public String welcomePage(Model model) {
        // model.addAttribute("liste", service.getAll());
        return "client/Login";
    }
}
