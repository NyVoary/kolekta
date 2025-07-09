package com.example.demo.repository;

import com.example.demo.entity.AssignationsTaches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssignationsTachesRepository extends JpaRepository<AssignationsTaches, Long> {

    @Query("SELECT a FROM AssignationsTaches a " +
           "WHERE a.employe.id = :idEmploye " +
           "AND (:date BETWEEN CAST(a.dateDebut AS localdate) AND CAST(a.dateFin AS localdate) " +
           "OR CAST(a.dateDebut AS localdate) = :date " +
           "OR CAST(a.dateFin AS localdate) = :date) " +
           "ORDER BY a.priorite.idPriorite, a.status.idStatus")
    List<AssignationsTaches> findTachesByEmployeAndDateOrderByPrioriteAndStatus(
            @Param("idEmploye") Long idEmploye,
            @Param("date") LocalDate date);
}