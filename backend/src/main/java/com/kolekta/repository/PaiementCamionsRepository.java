package com.kolekta.repository;

import com.kolekta.entity.PaiementCamions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementCamionsRepository extends JpaRepository<PaiementCamions, Long> {
    List<PaiementCamions> findByAssignationCamionIdAssignationCamion(Long idAssignationCamion);
}
