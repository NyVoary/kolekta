package com.example.demo.controller.admin;

import com.example.demo.dto.DateRangeDTO;
import com.example.demo.service.ClientStatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/admin/statistiques-clients")
public class ClientStatistiqueController {
    
    @Autowired
    private ClientStatistiqueService clientStatistiqueService;
    
    @GetMapping
    public String showStatForm(Model model) {
        DateRangeDTO dateRange = new DateRangeDTO();
        // Ne pas définir de dates par défaut pour afficher les stats générales
        model.addAttribute("dateRange", dateRange);
        
        // Charger les stats générales (toutes dates)
        Map<String, Object> stats = clientStatistiqueService.getAllStats(null);
        addStatsToModel(model, stats);
        
        return "admin/clients/statistique";
    }
    
    @PostMapping
    public String generateStats(@ModelAttribute("dateRange") DateRangeDTO dateRange, 
                              Model model) {
        Map<String, Object> stats;
        
        if (dateRange.getDateDebut() != null && dateRange.getDateFin() != null) {
            // Stats entre deux dates
            stats = clientStatistiqueService.getAllStats(dateRange);
        } else {
            // Stats générales
            stats = clientStatistiqueService.getAllStats(null);
            System.out.println("l'etat de statiqtique general"+stats);
        }
        
        addStatsToModel(model, stats);
        model.addAttribute("dateRange", dateRange); // Important: réinjecter l'objet
        
        return "admin/clients/statistique";
    }
    
    private void addStatsToModel(Model model, Map<String, Object> stats) {
        model.addAttribute("statsResume", stats.get("resume"));
        model.addAttribute("statsVilles", stats.get("villes"));
        model.addAttribute("statsSexe", stats.get("sexe"));
        model.addAttribute("topProduits", stats.get("topProduits"));
    }
}