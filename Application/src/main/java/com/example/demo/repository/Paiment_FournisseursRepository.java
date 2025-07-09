package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Paiment_Fournisseurs;

@Repository
public interface Paiment_FournisseursRepository extends JpaRepository<Paiment_Fournisseurs, Long> {
    @Query("SELECT pf FROM Paiment_Fournisseur pf JOIN pf.fournisseur f WHERE "
        + "(cast(:dateDebut as date) IS NULL OR pf.datePaiement >= :dateDebut) "
        + "AND (cast(:dateFin as date) IS NULL OR pf.datePaiement <= :dateFin) "
        + "AND (:nomFournisseur IS NULL OR LOWER(pf.fournisseur.nom) LIKE LOWER(concat('%', :nomFournisseur, '%')))")
    List<Paiment_Fournisseurs> findPaiementsByCriteria(
        @Param("dateDebut") LocalDate dateDebut,
        @Param("dateFin") LocalDate dateFin,
        @Param("nomFournisseur") String nomFournisseur);
}