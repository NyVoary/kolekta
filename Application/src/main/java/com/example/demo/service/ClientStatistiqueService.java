package com.example.demo.service;

import com.example.demo.dto.DateRangeDTO;
import com.example.demo.entity.ClientStatistique;
import com.example.demo.repository.ClientStatistiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientStatistiqueService {
    
    @Autowired
    private ClientStatistiqueRepository clientStatistiqueRepository;
    
    public Map<String, Object> getAllStats(DateRangeDTO dateRange) {
        Map<String, Object> stats = new HashMap<>();
        
        LocalDate dateDebut = (dateRange != null) ? dateRange.getDateDebut() : null;
        LocalDate dateFin = (dateRange != null) ? dateRange.getDateFin() : null;
        
        // Stats résumées
        ClientStatistique resume = (dateDebut != null && dateFin != null) 
            ? clientStatistiqueRepository.getResumeStats(dateDebut, dateFin)
            : clientStatistiqueRepository.getGeneralStats();
        stats.put("resume", resume);
        
        // Stats par ville
        List<ClientStatistique> villes = (dateDebut != null && dateFin != null)
            ? clientStatistiqueRepository.getStatsByVille(dateDebut, dateFin)
            : clientStatistiqueRepository.getGeneralStatsByVille();
        stats.put("villes", villes != null ? villes : new ArrayList<>());
        
        // Stats par sexe
        List<ClientStatistique> sexes = (dateDebut != null && dateFin != null)
            ? clientStatistiqueRepository.getStatsBySexe(dateDebut, dateFin)
            : clientStatistiqueRepository.getGeneralStatsBySexe();
        stats.put("sexe", sexes != null ? sexes : new ArrayList<>());
        
        // Top produits
        List<ClientStatistique> topProduits = (dateDebut != null && dateFin != null)
            ? clientStatistiqueRepository.getTopProduits(dateDebut, dateFin)
            : clientStatistiqueRepository.getGeneralTopProduits();
        stats.put("topProduits", topProduits != null ? topProduits : new ArrayList<>());
        
        return stats;
    }
}