package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Fournisseurs;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FornisseursRepository extends JpaRepository<Fournisseurs, Long> {
    @Query("SELECT COUNT(f) FROM Fournisseurs f WHERE f.dateAjout BETWEEN :dateDebut AND :dateFin")
    long countByDateAjoutBetween(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT f FROM Fournisseurs f WHERE f.dateAjout BETWEEN :dateDebut AND :dateFin")
    List<Fournisseurs> findNouveauxFournisseurs(@Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}
