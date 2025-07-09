package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Paiment_Fournisseur;

@Repository
public interface Paiment_FournisseurRepository extends JpaRepository<Paiment_Fournisseur, Long> {
    @Query("SELECT pf FROM Paiment_Fournisseur pf WHERE "
        + "(:dateDebut IS NULL OR pf.datePaiement >= :dateDebut) "
        + "AND (:dateFin IS NULL OR pf.datePaiement <= :dateFin) "
        + "AND (:nomFournisseur IS NULL OR LOWER(pf.fournisseur.nom) LIKE LOWER(concat('%', :nomFournisseur, '%')))")
    List<Paiment_Fournisseur> findPaiementsByCriteria(
        @Param("dateDebut") LocalDate dateDebut,
        @Param("dateFin") LocalDate dateFin,
        @Param("nomFournisseur") String nomFournisseur);
}