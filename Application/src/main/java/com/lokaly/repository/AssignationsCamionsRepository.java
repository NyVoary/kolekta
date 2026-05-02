package com.lokaly.repository;

import com.lokaly.entity.AssignationsCamions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignationsCamionsRepository extends JpaRepository<AssignationsCamions, Long> {
    List<AssignationsCamions> findByCamionIdCamion(Long idCamion);
    List<AssignationsCamions> findByEmployeIdEmploye(Long idEmploye);
}
