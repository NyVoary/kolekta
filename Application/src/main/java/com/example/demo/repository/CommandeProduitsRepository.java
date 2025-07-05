package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CommandeProduits;

@Repository
public interface CommandeProduitsRepository extends JpaRepository<CommandeProduits, Integer> {
    
    @Query(value = "SELECT p.nom, SUM(cp.quantite) as total_vendu " +
                   "FROM Commande_Produits cp " +
                   "JOIN Commandes c ON c.id_commande = cp.id_commande " +
                   "JOIN Commande_Paiement pai ON pai.id_commande = c.id_commande " +
                   "JOIN Paiement_Status ps ON ps.id_commande_paiement = pai.id_commande_paiement " +
                   "JOIN Produits p ON p.id_produit = cp.id_produit " +
                   "WHERE ps.status = 'Validé' " +
                   "GROUP BY cp.id_produit, p.nom " +
                   "ORDER BY total_vendu DESC " +
                   "LIMIT 3", 
           nativeQuery = true)
    List<Object[]> findTopProduitsPlusVendus();
    
    @Query(value = "SELECT p.nom, SUM(cp.quantite) as total_vendu " +
                   "FROM Commande_Produits cp " +
                   "JOIN Commandes c ON c.id_commande = cp.id_commande " +
                   "JOIN Commande_Paiement pai ON pai.id_commande = c.id_commande " +
                   "JOIN Paiement_Status ps ON ps.id_commande_paiement = pai.id_commande_paiement " +
                   "JOIN Produits p ON p.id_produit = cp.id_produit " +
                   "WHERE ps.status = 'Validé' " +
                   "GROUP BY cp.id_produit, p.nom " +
                   "ORDER BY total_vendu ASC " +
                   "LIMIT 3", 
           nativeQuery = true)
    List<Object[]> findTopProduitsMoinsVendus();

    @Query(value = "SELECT p.id_fournisseur, SUM(cp.quantite) as totalVendus FROM commande_produits cp JOIN produits p ON cp.id_produit = p.id_produit JOIN commandes c ON c.id_commande = cp.id_commande WHERE c.date_commande BETWEEN :dateDebut AND :dateFin GROUP BY p.id_fournisseur ORDER BY totalVendus DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopFournisseursByProduitsVendus(@Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}
