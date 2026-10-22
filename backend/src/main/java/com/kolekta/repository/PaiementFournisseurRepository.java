package com.kolekta.repository;

import com.kolekta.entity.PaiementFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementFournisseurRepository extends JpaRepository<PaiementFournisseur, Long> {
    List<PaiementFournisseur> findByFournisseurIdFournisseur(Long idFournisseur);
}
