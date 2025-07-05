package com.example.demo.service;

import com.example.demo.entity.Fournisseurs;
import com.example.demo.repository.FornisseursRepository;
import com.example.demo.repository.ProduitsRepository;
import com.example.demo.repository.CommandeProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class FournisseurStatService {
    private static final Logger log = LoggerFactory.getLogger(FournisseurStatService.class);
    @Autowired
    private FornisseursRepository fournisseursRepository;
    @Autowired
    private ProduitsRepository produitsRepository;
    @Autowired
    private CommandeProduitsRepository commandeProduitsRepository;

    public long getTotalFournisseurs(LocalDate debut, LocalDate fin) {
        long total = fournisseursRepository.countByDateAjoutBetween(debut, fin);
        log.info("[STATS] Total fournisseurs entre {} et {} : {}", debut, fin, total);
        return total;
    }

    public List<Fournisseurs> getNouveauxFournisseurs(LocalDate debut, LocalDate fin) {
        List<Fournisseurs> nouveaux = fournisseursRepository.findNouveauxFournisseurs(debut, fin);
        log.info("[STATS] Nouveaux fournisseurs entre {} et {} : {}", debut, fin, nouveaux.size());
        return nouveaux;
    }

    public Double getMoyenneProduitsParFournisseur(LocalDate debut, LocalDate fin) {
        List<Object[]> counts = produitsRepository.countProduitsGroupByFournisseur(debut, fin);
        if (counts == null || counts.isEmpty()) {
            log.info("[STATS] Aucune donnée pour la moyenne produits/fournisseur sur la période.");
            return 0.0;
        }
        double total = 0;
        for (Object[] row : counts) {
            total += ((Number) row[1]).doubleValue();
        }
        double moyenne = total / counts.size();
        log.info("[STATS] Moyenne produits/fournisseur entre {} et {} : {}", debut, fin, moyenne);
        return moyenne;
    }

    public List<Map<String, Object>> getTopFournisseursByVentes(LocalDate debut, LocalDate fin) {
        List<Object[]> rows = commandeProduitsRepository.findTopFournisseursByProduitsVendus(debut, fin);
        log.info("[STATS] Top fournisseurs (ventes) entre {} et {} : {}", debut, fin, rows.size());
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("idFournisseur", row[0]);
            map.put("totalVendus", row[1]);
            result.add(map);
        }
        return result;
    }
}
