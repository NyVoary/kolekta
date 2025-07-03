package com.example.demo.repository;

import java.util.List;

import com.example.demo.view.VCamionEtat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VCamionEtatRepository extends JpaRepository<VCamionEtat, Long> {
        List<VCamionEtat> findByEtatCamion(String etatCamion);
}
 