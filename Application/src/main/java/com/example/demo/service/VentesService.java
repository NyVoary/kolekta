package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VentesService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getTop3ProduitsPlusVendus() {
        // Debug: vérifier les données de base
        System.out.println("=== DEBUG TABLES ===");
        
        String countCommandes = "SELECT COUNT(*) FROM Commandes";
        String countProduits = "SELECT COUNT(*) FROM Commande_Produits";
        String countPaiements = "SELECT COUNT(*) FROM Commande_Paiement";
        String countStatus = "SELECT COUNT(*) FROM Paiement_Status";
        String countValide = "SELECT COUNT(*) FROM Paiement_Status WHERE TRIM(status) = 'Valid’'";
        
        System.out.println("Commandes: " + jdbcTemplate.queryForObject(countCommandes, Integer.class));
        System.out.println("Commande_Produits: " + jdbcTemplate.queryForObject(countProduits, Integer.class));
        System.out.println("Commande_Paiement: " + jdbcTemplate.queryForObject(countPaiements, Integer.class));
        System.out.println("Paiement_Status: " + jdbcTemplate.queryForObject(countStatus, Integer.class));
        System.out.println("Status Validé: " + jdbcTemplate.queryForObject(countValide, Integer.class));
        
        // Test simple sans JOIN
        String simpleSql = "SELECT nom FROM Produits LIMIT 3";
        List<Map<String, Object>> produits = jdbcTemplate.queryForList(simpleSql);
        System.out.println("Produits trouvés: " + produits.size());
        
        String sql = """
            SELECT p.nom, SUM(cp.quantite) AS total_vendu
            FROM Commande_Produits cp
            JOIN Commandes c ON cp.id_commande = c.id_commande
            JOIN Commande_Paiement pai ON pai.id_commande = c.id_commande
            JOIN Paiement_Status ps ON ps.id_commande_paiement = pai.id_commande_paiement
            JOIN Produits p ON p.id_produit = cp.id_produit
            WHERE unaccent(TRIM(ps.status)) ILIKE 'valid%'
            GROUP BY p.nom
            ORDER BY total_vendu DESC
            LIMIT 3;
        """;



        List<Map<String, Object>> resultats = jdbcTemplate.queryForList(sql);
        
        List<String> noms = new ArrayList<>();
        List<Long> quantites = new ArrayList<>();
        
        System.out.println("=== TOP PLUS VENDUS (VentesService) ===");
        System.out.println("Nombre de résultats: " + resultats.size());
        
        for (Map<String, Object> row : resultats) {
            String nom = (String) row.get("nom");
            Long quantite = ((Number) row.get("total_vendu")).longValue();
            
            noms.add(nom);
            quantites.add(quantite);
            
            System.out.println("Produit: " + nom + ", Quantité: " + quantite);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("noms", noms);
        result.put("quantites", quantites);
        
        return result;
    }

    public Map<String, Object> getTop3ProduitsMoinsVendus() {
    String sql = """
        SELECT p.nom, SUM(cp.quantite) AS total_vendu
        FROM Commande_Produits cp
        JOIN Commandes c ON cp.id_commande = c.id_commande
        JOIN Commande_Paiement pai ON pai.id_commande = c.id_commande
        JOIN Paiement_Status ps ON ps.id_commande_paiement = pai.id_commande_paiement
        JOIN Produits p ON p.id_produit = cp.id_produit
        WHERE unaccent(TRIM(ps.status)) ILIKE 'valid%'
        GROUP BY p.nom
        ORDER BY total_vendu ASC
        LIMIT 3;
    """;



        List<Map<String, Object>> resultats = jdbcTemplate.queryForList(sql);
        
        List<String> noms = new ArrayList<>();
        List<Long> quantites = new ArrayList<>();
        
        System.out.println("=== TOP MOINS VENDUS (VentesService) ===");
        System.out.println("Nombre de résultats: " + resultats.size());
        
        for (Map<String, Object> row : resultats) {
            String nom = (String) row.get("nom");
            Long quantite = ((Number) row.get("total_vendu")).longValue();
            
            noms.add(nom);
            quantites.add(quantite);
            
            System.out.println("Produit: " + nom + ", Quantité: " + quantite);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("noms", noms);
        result.put("quantites", quantites);
        
        return result;
    }
}