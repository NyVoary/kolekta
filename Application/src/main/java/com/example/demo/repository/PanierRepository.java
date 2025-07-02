package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {

    Optional<Panier> findByIdClient(Long idClient);

    boolean existsByIdClient(Long idClient);

    @Query("""
        SELECT p FROM Panier p
        LEFT JOIN FETCH p.panierProduits pp
        LEFT JOIN FETCH pp.produit
        WHERE p.idClient = :idClient
    """)
    Optional<Panier> findByIdClientWithProduits(@Param("idClient") Long idClient);
}
