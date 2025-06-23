package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String welcomePage(Model model) {
        return "admin/home";
    }
    @GetMapping("admin/ajout-produit")
     public String addProduct(Model model) {
        return "admin/ajout-produit";
    }
}

