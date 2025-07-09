package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Paiment_Camions;

@Repository
public interface Paiment_CamionsRepository extends JpaRepository<Paiment_Camions, Long> {

    @Query("SELECT pc FROM Paiment_Camion pc JOIN pc.commandeCamion cc JOIN cc.assignationCamion ac JOIN ac.camion c WHERE "
        + "(cast(:dateDebut as timestamp) IS NULL OR pc.datePaiement >= :dateDebut) "
        + "AND (cast(:dateFin as timestamp) IS NULL OR pc.datePaiement <= :dateFin) "
        + "AND (:immatriculation IS NULL OR LOWER(c.immatriculation) LIKE LOWER(concat('%', :immatriculation, '%')))")
    List<Paiment_Camions> findPaiementsByCriteria(
        @Param("dateDebut") LocalDateTime dateDebut,
        @Param("dateFin") LocalDateTime dateFin,
        @Param("immatriculation") String immatriculation);
}