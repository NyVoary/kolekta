package com.example.demo.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ProduitsRepository;
import com.example.demo.repository.DisponibilitesFournisseurRepository;
import com.example.demo.repository.CommandeProduitsRepository;

@Service
public class StatistiquesService {

    @Autowired
    private ProduitsRepository produitsRepository;
    
    @Autowired
    private DisponibilitesFournisseurRepository disponibilitesRepository;
    
    @Autowired
    private CommandeProduitsRepository commandeProduitsRepository;
    
    @Autowired
    private VentesService ventesService;

    public Map<String, Object> getStatistiquesGenerales() {
        Map<String, Object> stats = new HashMap<>();
        
        // Nombre total de produits
        long totalProduits = produitsRepository.count();
        stats.put("totalProduits", totalProduits);
        
        // Nombre de produits en rupture de stock
        Long produitsRupture = disponibilitesRepository.countProduitsEnRupture();
        stats.put("produitsRupture", produitsRupture != null ? produitsRupture : 0);
        
        // Nombre de produits en stock bas
        Long produitsStockBas = disponibilitesRepository.countProduitsStockBas();
        stats.put("produitsStockBas", produitsStockBas != null ? produitsStockBas : 0);
        
        // Nouveaux produits ajoutés ce mois
        LocalDate debutMois = LocalDate.now().withDayOfMonth(1);
        LocalDate finMois = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        
        long nouveauxProduits = produitsRepository.findAll().stream()
            .filter(p -> p.getDate_ajout() != null && 
                        !p.getDate_ajout().isBefore(debutMois) && 
                        !p.getDate_ajout().isAfter(finMois))
            .count();
        stats.put("nouveauxProduits", nouveauxProduits);
        
        return stats;
    }
    
    public Map<String, Object> getTop3ProduitsPlusVendus() {
        List<Object[]> resultats = commandeProduitsRepository.findTopProduitsPlusVendus();
        Map<String, Object> top3 = new HashMap<>();
        
        // Debug
        System.out.println("=== TOP PLUS VENDUS ===");
        System.out.println("Nombre de résultats: " + resultats.size());
        for (Object[] row : resultats) {
            System.out.println("Produit: " + row[0] + ", Quantité: " + row[1]);
        }
        
        List<String> noms = resultats.stream()
            .limit(3)
            .map(row -> (String) row[0])
            .collect(Collectors.toList());
            
        List<Long> quantites = resultats.stream()
            .limit(3)
            .map(row -> ((Number) row[1]).longValue())
            .collect(Collectors.toList());
        
        top3.put("noms", noms);
        top3.put("quantites", quantites);
        
        System.out.println("Noms: " + noms);
        System.out.println("Quantités: " + quantites);
        
        return top3;
    }
    
    public Map<String, Object> getTop3ProduitsMoinsVendus() {
        List<Object[]> resultats = commandeProduitsRepository.findTopProduitsMoinsVendus();
        Map<String, Object> top3 = new HashMap<>();
        
        // Debug
        System.out.println("=== TOP MOINS VENDUS ===");
        System.out.println("Nombre de résultats: " + resultats.size());
        for (Object[] row : resultats) {
            System.out.println("Produit: " + row[0] + ", Quantité: " + row[1]);
        }
        
        List<String> noms = resultats.stream()
            .limit(3)
            .map(row -> (String) row[0])
            .collect(Collectors.toList());
            
        List<Long> quantites = resultats.stream()
            .limit(3)
            .map(row -> ((Number) row[1]).longValue())
            .collect(Collectors.toList());
        
        top3.put("noms", noms);
        top3.put("quantites", quantites);
        
        System.out.println("Noms: " + noms);
        System.out.println("Quantités: " + quantites);
        
        return top3;
    }
    
    public Map<String, Object> getToutesLesStatistiques() {
        Map<String, Object> allStats = new HashMap<>();
        
        Map<String, Object> statsGenerales = getStatistiquesGenerales();
        Map<String, Object> top3Plus = ventesService.getTop3ProduitsPlusVendus();
        Map<String, Object> top3Moins = ventesService.getTop3ProduitsMoinsVendus();
        
        allStats.put("generale", statsGenerales);
        allStats.put("top3Plus", top3Plus);
        allStats.put("top3Moins", top3Moins);
        
        return allStats;
    }
}