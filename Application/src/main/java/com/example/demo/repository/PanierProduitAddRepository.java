package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Panier_Produits;

public interface PanierProduitAddRepository extends JpaRepository<Panier_Produits, Integer> {
    // Tu peux ajouter ici plus tard des méthodes comme :
    // List<PanierProduitAdd> findByIdPanier(Long idPanier);
}