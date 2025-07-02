package com.example.demo.repository;

import com.example.demo.entity.CommandeProduits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface CommandeProduitsRepository extends JpaRepository<CommandeProduits, Long> {
    @Query(value = "SELECT p.id_fournisseur, SUM(cp.quantite) as totalVendus FROM commande_produits cp JOIN produits p ON cp.id_produit = p.id_produit JOIN commandes c ON c.id_commande = cp.id_commande WHERE c.date_commande BETWEEN :dateDebut AND :dateFin GROUP BY p.id_fournisseur ORDER BY totalVendus DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopFournisseursByProduitsVendus(@Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}
