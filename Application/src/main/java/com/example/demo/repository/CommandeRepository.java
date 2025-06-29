package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.example.demo.entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @Query("SELECT c FROM Commande c WHERE c.client.id = :clientId AND EXISTS (SELECT cs FROM CommandeStatus cs JOIN cs.status s WHERE cs.commande = c AND s.libelle = 'Validée') ORDER BY c.dateCommande DESC")
    List<Commande> findValidatedCommandesByClient(@Param("clientId") Long clientId);
}
