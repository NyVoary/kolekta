package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Paiment_Camions;
import com.example.demo.service.CamionsService;
import com.example.demo.service.Paiment_CamionsService;

@Controller
public class Paiment_CamionsController {

    @Autowired
    private Paiment_CamionsService paiementService;

    @Autowired
    private CamionsService camionService;

    @GetMapping("/admin/paiement-camions")
    public String afficherPaiements(
        @RequestParam(required = false) String dateDebut,
        @RequestParam(required = false) String dateFin,
        @RequestParam(required = false) String immatriculation,
        Model model) {

        List<Paiment_Camions> paiements = paiementService.rechercherPaiements(
            dateDebut, dateFin, immatriculation);
        
        model.addAttribute("paiements", paiements);
        model.addAttribute("camions", camionService.getAllCamions());
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("immatriculation", immatriculation);
        
        return "admin/paiement-camions";
    }
}