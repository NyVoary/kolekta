package com.lokaly.repository;

import com.lokaly.entity.PaiementCamions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementCamionsRepository extends JpaRepository<PaiementCamions, Long> {
    List<PaiementCamions> findByAssignationCamionIdAssignationCamion(Long idAssignationCamion);
}
