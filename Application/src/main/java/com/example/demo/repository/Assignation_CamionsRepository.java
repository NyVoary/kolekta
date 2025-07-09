package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Assignation_Camions;

@Repository
public interface Assignation_CamionsRepository extends JpaRepository<Assignation_Camions, Long> {
}