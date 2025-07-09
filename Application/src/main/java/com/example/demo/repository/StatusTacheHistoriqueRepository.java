package com.example.demo.repository;

import com.example.demo.entity.AssignationTache;
import com.example.demo.entity.StatusTacheHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface StatusTacheHistoriqueRepository extends JpaRepository<StatusTacheHistorique, Long> {

    // Historique d'une tâche spécifique trié par date
    List<StatusTacheHistorique> findByTacheOrderByDateDesc(AssignationTache tache);

    // Trouver tous les changements faits par un employé
    List<StatusTacheHistorique> findByModifieParId(Long employeId);

    // Dernier statut pour chaque tâche
    @Query("SELECT sh FROM StatusTacheHistorique sh " +
           "WHERE sh.date = (SELECT MAX(sh2.date) FROM StatusTacheHistorique sh2 WHERE sh2.tache = sh.tache)")
    List<StatusTacheHistorique> findLastStatusForAllTasks();

    // Historique entre deux dates
    @Query("SELECT sh FROM StatusTacheHistorique sh " +
           "WHERE sh.date BETWEEN :start AND :end " +
           "ORDER BY sh.date DESC")
    List<StatusTacheHistorique> findBetweenDates(@Param("start") LocalDateTime start, 
                                               @Param("end") LocalDateTime end);

    // Trouver les changements vers un statut spécifique
    List<StatusTacheHistorique> findByStatus(AssignationTache.StatusTache status);

    // Requête optimisée avec jointures
    @Query("SELECT sh FROM StatusTacheHistorique sh " +
           "JOIN FETCH sh.tache " +
           "JOIN FETCH sh.modifiePar " +
           "WHERE sh.tache.idTache = :tacheId")
    List<StatusTacheHistorique> findFullHistoryByTaskId(@Param("tacheId") Long tacheId);
}