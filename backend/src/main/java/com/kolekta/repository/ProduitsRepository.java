package com.kolekta.repository;

import com.kolekta.entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitsRepository extends JpaRepository<Produits, Long> {
    List<Produits> findByCategorieIdCategorie(Long idCategorie);
    List<Produits> findByFournisseurIdFournisseur(Long idFournisseur);
    List<Produits> findByNomContainingIgnoreCase(String nom);
}
