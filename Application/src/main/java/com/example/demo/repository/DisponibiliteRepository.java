package com.example.demo.repository;

import com.example.demo.entity.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
        // Somme des quantités pour un produit donné
    @Query("SELECT COALESCE(SUM(d.quantite), 0) FROM Disponibilite d WHERE d.produit.id = :produitId")
    int sommeQuantiteParProduit(Long produitId);

    // Si tu veux la somme des quantités pour un fournisseur donné
    @Query("SELECT COALESCE(SUM(d.quantite), 0) FROM Disponibilite d WHERE d.fournisseur.id = :fournisseurId")
    int sommeQuantiteParFournisseur(@Param("fournisseurId") Long fournisseurId);
}
