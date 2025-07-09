package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Commande_Camions;

@Repository
public interface Commande_CamionsRepository extends JpaRepository<Commande_Camions, Long> {
}