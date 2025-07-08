package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoriqueAchatService {
    private static final Logger logger = LoggerFactory.getLogger(HistoriqueAchatService.class);

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeProduitsRepository commandeProduitRepository;

    private String removeAccents(String input) {
        if (input == null) return null;
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        normalized = normalized.replace('\u201a', 'e');
        normalized = normalized.replace('’', 'e');
        normalized = normalized.replace('é', 'e');
        normalized = normalized.replace('É', 'E');
        normalized = normalized.replaceAll("[^\\u0000-\\u007F]", "e");
        return normalized;
    }

    public List<HistoriqueAchat> getHistoriqueAchatByClient(Long clientId) {
        logger.info("Recherche de l'historique d'achat pour le client {}", clientId);
        List<Commandes> toutesCommandes = commandeRepository.findAll();
        List<HistoriqueAchat> historique = new ArrayList<>();

        for (Commandes c : toutesCommandes) {
            if (c.getClient() != null && clientId.equals(c.getClient().getIdClient())) {
                if (c.getStatuts() != null && c.getStatuts().stream().anyMatch(cs -> {
                    String libelle = cs.getStatus() != null ? cs.getStatus().getLibelle() : null;
                    String libelleNormalise = removeAccents(libelle != null ? libelle.trim().toLowerCase() : "");
                    return "validee".equals(libelleNormalise);
                })) {
                    List<CommandeProduits> produits = commandeProduitRepository.findByCommandeId(c.getId());
                    List<String> nomsProduits = produits.stream()
                            .map(cp -> cp.getProduit().getNom())
                            .collect(Collectors.toList());
                    historique.add(new HistoriqueAchat(c.getId(), c.getDateCommande(), c.getMontantTotal(), nomsProduits));
                }
            }
        }
        logger.info("{} commandes validées trouvées pour le client {}", historique.size(), clientId);
        return historique;
    }

    public List<HistoriqueAchat> getHistoriqueAchatByClientAndDate(Long clientId, LocalDate dateDebut, LocalDate dateFin) {
        logger.info("Recherche de l'historique d'achat pour le client {} entre {} et {}", clientId, dateDebut, dateFin);
        List<Commandes> toutesCommandes = commandeRepository.findAll();
        List<HistoriqueAchat> historique = new ArrayList<>();

        for (Commandes c : toutesCommandes) {
            if (c.getClient() != null && clientId.equals(c.getClient().getIdClient())) {
                LocalDate dateCommande = c.getDateCommande();
                boolean inRange = (dateDebut == null || !dateCommande.isBefore(dateDebut)) &&
                                  (dateFin == null || !dateCommande.isAfter(dateFin));
                if (!inRange) continue;

                if (c.getStatuts() != null && c.getStatuts().stream().anyMatch(cs -> {
                    String libelle = cs.getStatus() != null ? cs.getStatus().getLibelle() : null;
                    String libelleNormalise = removeAccents(libelle != null ? libelle.trim().toLowerCase() : "");
                    return "validee".equals(libelleNormalise);
                })) {
                    List<CommandeProduits> produits = commandeProduitRepository.findByCommandeId(c.getId());
                    List<String> nomsProduits = produits.stream()
                            .map(cp -> cp.getProduit().getNom())
                            .collect(Collectors.toList());
                    historique.add(new HistoriqueAchat(c.getId(), c.getDateCommande(), c.getMontantTotal(), nomsProduits));
                }
            }
        }
        logger.info("{} commandes validées trouvées pour le client {} dans la période", historique.size(), clientId);
        return historique;
    }
}
