package com.example.demo.repository;

import com.example.demo.entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
}