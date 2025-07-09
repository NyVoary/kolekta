package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Paiment_Camions;

@Repository
public interface Paiment_CamionsRepository extends JpaRepository<Paiment_Camions, Long> {

    @Query("SELECT pc FROM Paiment_Camions pc " +
           "JOIN pc.commandeCamion cc " +
           "JOIN cc.assignationCamion ac " +
           "JOIN ac.camion c " +
           "WHERE (cast(:dateDebut as date) IS NULL OR CAST(pc.datePaiement as date) >= :dateDebut) " +
           "AND (cast(:dateFin as date) IS NULL OR CAST(pc.datePaiement as date) <= :dateFin) " +
           "AND (:immatriculation IS NULL OR c.immatriculation ILIKE %:immatriculation%)")
    List<Paiment_Camions> findPaiementsByCriteria(
        @Param("dateDebut") LocalDate dateDebut,
        @Param("dateFin") LocalDate dateFin,
        @Param("immatriculation") String immatriculation);
}