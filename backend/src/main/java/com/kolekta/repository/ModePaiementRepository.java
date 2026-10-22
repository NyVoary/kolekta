package com.kolekta.repository;

import com.kolekta.entity.ModePaiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModePaiementRepository extends JpaRepository<ModePaiement, Long> {
    Optional<ModePaiement> findByLibelleIgnoreCase(String libelle);
}
