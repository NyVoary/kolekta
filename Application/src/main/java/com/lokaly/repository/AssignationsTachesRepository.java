package com.lokaly.repository;

import com.lokaly.entity.AssignationsTaches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignationsTachesRepository extends JpaRepository<AssignationsTaches, Long> {
    List<AssignationsTaches> findByEmployeIdEmploye(Long idEmploye);
    List<AssignationsTaches> findByStatusTacheCode(String code);
    List<AssignationsTaches> findByPrioriteCode(String code);
}
