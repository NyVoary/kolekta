package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AssignationCamion;

@Repository
public interface AssignationCamionRepository extends JpaRepository<AssignationCamion, Integer> {

    // Tu peux ajouter ici des méthodes personnalisées si besoin plus tard
}
