package com.kolekta.repository;

import com.kolekta.entity.AssignationsTaches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignationsTachesRepository extends JpaRepository<AssignationsTaches, Long> {
    List<AssignationsTaches> findByEmployeIdEmploye(Long idEmploye);
    List<AssignationsTaches> findByStatusTacheLibelle(String libelle);
    List<AssignationsTaches> findByPrioriteLibelle(String libelle);
}
