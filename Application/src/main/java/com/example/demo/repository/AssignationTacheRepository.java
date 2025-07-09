package com.example.demo.repository;

import com.example.demo.entity.AssignationTache;
import com.example.demo.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AssignationTacheRepository extends JpaRepository<AssignationTache, Long> {

    // Trouver les tâches par employé
    List<AssignationTache> findByEmploye(Employe employe);

    List<AssignationTache> findByEmployeOrderByDateDebutDesc(Employe employe);

    // Trouver les tâches par statut
    List<AssignationTache> findByStatusActuelle(AssignationTache.StatusTache status);

    // Trouver les tâches par priorité
    List<AssignationTache> findByPriorite(AssignationTache.Priorite priorite);

    // Trouver les tâches entre deux dates
    List<AssignationTache> findByDateDebutBetween(LocalDateTime start, LocalDateTime end);

    // Requête personnalisée pour les tâches urgentes non terminées
    @Query("SELECT t FROM AssignationTache t WHERE t.priorite = 'Urgente' AND t.statusActuelle <> 'Terminee'")
    List<AssignationTache> findUrgentesNonTerminees();

    // Requête avec jointure pour optimiser les chargements
    @Query("SELECT t FROM AssignationTache t JOIN FETCH t.employe WHERE t.idTache = :id")
    AssignationTache findByIdWithEmploye(@Param("id") Long id);

    // Compter les tâches par statut
    @Query("SELECT COUNT(t) FROM AssignationTache t WHERE t.statusActuelle = :status")
    long countByStatus(@Param("status") AssignationTache.StatusTache status);
}