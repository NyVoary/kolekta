package com.kolekta.repository;

import com.kolekta.entity.DisponibilitesFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisponibilitesFournisseurRepository extends JpaRepository<DisponibilitesFournisseur, Long> {
    Optional<DisponibilitesFournisseur> findByFournisseurIdFournisseurAndProduitIdProduit(Long idFournisseur, Long idProduit);
    List<DisponibilitesFournisseur> findByFournisseurIdFournisseur(Long idFournisseur);
    List<DisponibilitesFournisseur> findByProduitIdProduit(Long idProduit);
}
