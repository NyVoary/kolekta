package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Camions;

@Repository
public interface CamionsRepository extends JpaRepository<Camions, Long> {
}