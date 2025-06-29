package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    // Tu pourras ajouter des méthodes personnalisées plus tard, par ex :
    // List<Panier> findByIdUtilisateur(Long idUtilisateur);
}