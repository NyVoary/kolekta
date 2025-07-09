package com.example.demo.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AssignationTache;

@Repository
public interface AssignationTacheRepository extends JpaRepository<AssignationTache, Long> {

    @Query(value = """
        SELECT
            COUNT(*) FILTER (WHERE statusactuelle = 'En attente') AS en_attente,
            COUNT(*) FILTER (WHERE statusactuelle = 'En cours')   AS en_cours,
            COUNT(*) FILTER (WHERE statusactuelle = 'Terminée')   AS termine,
            COUNT(*) as total
        FROM assignations_taches
    """, nativeQuery = true)
    Map<String, Object> getStatique();
}