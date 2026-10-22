package com.lokaly.repository;

import com.lokaly.entity.ModePaiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModePaiementRepository extends JpaRepository<ModePaiement, Long> {
    Optional<ModePaiement> findByLibelleIgnoreCase(String libelle);
}
