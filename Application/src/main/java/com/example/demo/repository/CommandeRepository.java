package com.example.demo.repository;

<<<<<<< HEAD
import com.example.demo.entity.*;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commandes, Long> {
    @EntityGraph(attributePaths = { "paiements", "commandeStatusList" })
    @Query("SELECT c FROM Commandes c WHERE c.client.idClient = :clientId")
    List<Commandes> findByClientId(@Param("clientId") Long clientId);;

    List<Commandes> findByDateCommande(Date dateCommande);

    List<Commandes> findByVilleLivraison(String villeLivraison);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Commandes c SET c.dateCommande = :newDate WHERE c.id = :id")
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
    List<Commandes> findByClientIdWithFilters(
            @Param("clientId") Long clientId,
            @Param("statut") String statut,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo);
}
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.example.demo.entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @Query("SELECT c FROM Commande c WHERE c.client.id = :clientId AND EXISTS (SELECT cs FROM CommandeStatus cs JOIN cs.status s WHERE cs.commande = c AND s.libelle = 'Validée') ORDER BY c.dateCommande DESC")
    List<Commande> findValidatedCommandesByClient(@Param("clientId") Long clientId);
}
>>>>>>> Robel
