package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CommandeCamion;

@Repository
public interface Commande_CamionsRepository extends JpaRepository<CommandeCamion, Long> {
}