package com.example.demo.repository;

import com.example.demo.entity.Commande;
import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    
    List<Commande> findByClientOrderByDateCommandeDesc(Client client);
    
    @Query("SELECT c FROM Commande c JOIN FETCH c.produits WHERE c.client.idClient = :clientId ORDER BY c.dateCommande DESC")
    List<Commande> findWithProduitsByClientId(@Param("clientId") int clientId);
    
    @Query("SELECT DISTINCT c FROM Commande c LEFT JOIN FETCH c.produits WHERE c.client.idClient = :clientId ORDER BY c.dateCommande DESC")
    List<Commande> findAllByClientIdWithProduits(@Param("clientId") int clientId);
}