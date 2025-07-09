package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DisponibilitesFournisseur;

@Repository
public interface DisponibilitesFournisseurRepository extends JpaRepository<DisponibilitesFournisseur, Integer> {
    
    @Query("SELECT COUNT(d) FROM DisponibilitesFournisseur d WHERE d.quantiteDisponible = 0")
    Long countProduitsEnRupture();
    
    @Query("SELECT COUNT(d) FROM DisponibilitesFournisseur d WHERE d.quantiteDisponible > 0 AND d.quantiteDisponible <= 15")
    Long countProduitsStockBas();
}