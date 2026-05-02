package com.lokaly.repository;

import com.lokaly.entity.VCamionEtat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VCamionEtatRepository extends JpaRepository<VCamionEtat, Long> {
    List<VCamionEtat> findByEtatCamion(String etatCamion);
}
