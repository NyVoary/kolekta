package com.example.demo.repository;

import com.example.demo.entity.Commande;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @EntityGraph(attributePaths = {"paiements", "commandeStatusList"})
    List<Commande> findByIdClient(Long idClient);
    List<Commande> findByDateCommande(Date dateCommande);
    List<Commande> findByVilleLivraison(String villeLivraison);

    @Query("UPDATE Commande c SET c.dateCommande = :newDate WHERE c.idCommande = :id")
    @Modifying
    void updateDateCommande(@Param("id") Long id, @Param("newDate") LocalDate newDate);

    @Query(value = """
    SELECT c.* FROM commandes c
    JOIN commande_status cs ON c.id_commande = cs.id_commande
    JOIN status s ON cs.id_status = s.id_status
    WHERE c.id_client = :clientId
    AND (:statut IS NULL OR s.libelle LIKE %:statut%)
    AND (CAST(:dateFrom AS date) IS NULL OR c.date_commande >= :dateFrom)
    AND (CAST(:dateTo AS date) IS NULL OR c.date_commande <= :dateTo)
    ORDER BY c.date_commande DESC
    """, nativeQuery = true)
    List<Commande> findByClientIdWithFilters(
        @Param("clientId") Long clientId,
        @Param("statut") String statut,
        @Param("dateFrom") LocalDate dateFrom,
        @Param("dateTo") LocalDate dateTo);
}