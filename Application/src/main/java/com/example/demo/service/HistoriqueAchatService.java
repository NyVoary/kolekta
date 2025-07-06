package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.CommandeProduitRepository;
import com.example.demo.repository.CommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoriqueAchatService {
    private static final Logger logger = LoggerFactory.getLogger(HistoriqueAchatService.class);

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    /**
     * Supprime les accents d'une chaîne (normalisation Unicode) et corrige les
     * caractères fautifs
     */
    private String removeAccents(String input) {
        if (input == null)
            return null;
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // Remplacement explicite des caractères fautifs (201a, etc.) par 'e'
        normalized = normalized.replace('\u201a', 'e'); // virgule basse
        normalized = normalized.replace('’', 'e'); // apostrophe courbe
        normalized = normalized.replace('é', 'e'); // é normal
        normalized = normalized.replace('É', 'E'); // É majuscule
        // On retire tout caractère non ASCII (optionnel)
        normalized = normalized.replaceAll("[^\\u0000-\\u007F]", "e");
        return normalized;
    }

    public List<HistoriqueAchat> getHistoriqueAchatByClient(Long clientId) {
        logger.info("Recherche de l'historique d'achat pour le client {}", clientId);
        List<Commandes> toutesCommandes = commandeRepository.findAll();
        logger.info("Commandes trouvées en base pour le client {} :", clientId);
        List<HistoriqueAchat> historique = new ArrayList<>();
        for (Commandes c : toutesCommandes) {
            if (c.getClient() != null && c.getClient().getIdClient() != null
                    && c.getClient().getIdClient().equals(clientId)) {
                logger.info("Commande id={} date={} montant={} ...", c.getId(), c.getDateCommande(),
                        c.getMontantTotal());
                if (c.getStatuts() == null) {
                    logger.warn("Commande id={} : statuts=null", c.getId());
                } else {
                    logger.info("Commande id={} : statuts.size()={}", c.getId(), c.getStatuts().size());
                    for (CommandeStatus cs : c.getStatuts()) {
                        String libelle = cs.getStatus() != null ? cs.getStatus().getLibelle() : null;
                        // Affichage de la séquence Unicode
                        StringBuilder unicodeSeq = new StringBuilder();
                        if (libelle != null) {
                            for (char ch : libelle.toCharArray()) {
                                unicodeSeq.append(String.format("%04x ", (int) ch));
                            }
                        }
                        String libelleNormalise = removeAccents(libelle != null ? libelle.trim().toLowerCase() : "");
                        logger.info(
                                "  -> Statut: id={} libelle='{}' (type: {}, length: {}) == 'Validée'? {} | Unicode: {} | Normalisé: {}",
                                cs.getId(), libelle, libelle != null ? libelle.getClass().getName() : "null",
                                libelle != null ? libelle.length() : 0,
                                (libelle != null && libelle.trim().equalsIgnoreCase("Validée")),
                                unicodeSeq.toString().trim(), libelleNormalise);
                    }
                }
                if (c.getStatuts() != null && c.getStatuts().stream().anyMatch(cs -> {
                    String libelle = cs.getStatus() != null ? cs.getStatus().getLibelle() : null;
                    String libelleNormalise = removeAccents(libelle != null ? libelle.trim().toLowerCase() : "");
                    return "validee".equals(libelleNormalise);
                })) {
                    List<CommandeProduits> produits = commandeProduitRepository.findByCommandeId(c.getId());
                    List<String> nomsProduits = produits.stream().map(cp -> cp.getProduit().getNom())
                            .collect(Collectors.toList());
                    historique.add(new HistoriqueAchat(
                            c.getId(),
                            c.getDateCommande(),
                            c.getMontantTotal(),
                            nomsProduits));
                }
            }
        }
        logger.info("{} commandes validées trouvées pour le client {} (filtrage Java)", historique.size(), clientId);
        return historique;
    }

    public List<HistoriqueAchat> getHistoriqueAchatByClientAndDate(Long clientId, LocalDate dateDebut,
            LocalDate dateFin) {
        logger.info("Recherche de l'historique d'achat pour le client {} entre {} et {}", clientId, dateDebut, dateFin);
        List<Commandes> toutesCommandes = commandeRepository.findAll();
        List<HistoriqueAchat> historique = new ArrayList<>();
        for (Commandes c : toutesCommandes) {
            if (c.getClient() != null && c.getClient().getIdClient() != null
                    && c.getClient().getIdClient().equals(clientId)) {
                if (c.getDateCommande() != null) {
                    LocalDate dateCommande = c.getDateCommande();
                    boolean inRange = (dateDebut == null || !dateCommande.isBefore(dateDebut))
                            && (dateFin == null || !dateCommande.isAfter(dateFin));
                    if (!inRange)
                        continue;
                }
                if (c.getStatuts() != null && c.getStatuts().stream().anyMatch(cs -> {
                    String libelle = cs.getStatus() != null ? cs.getStatus().getLibelle() : null;
                    String libelleNormalise = removeAccents(libelle != null ? libelle.trim().toLowerCase() : "");
                    return "validee".equals(libelleNormalise);
                })) {
                    List<CommandeProduits> produits = commandeProduitRepository.findByCommandeId(c.getId());
                    List<String> nomsProduits = produits.stream().map(cp -> cp.getProduit().getNom())
                            .collect(Collectors.toList());
                    historique.add(new HistoriqueAchat(
                            c.getId(),
                            c.getDateCommande(),
                            c.getMontantTotal(),
                            nomsProduits));
                }
            }
        }
        logger.info("{} commandes validées trouvées pour le client {} dans la période (filtrage Java)",
                historique.size(), clientId);
        return historique;
    }
}
