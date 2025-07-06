package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PanierProduits;

public interface PanierProduitAddRepository extends JpaRepository<PanierProduits, Long> {
    // Tu peux ajouter ici plus tard des méthodes comme :
    // List<PanierProduitAdd> findByIdPanier(Long idPanier);
}