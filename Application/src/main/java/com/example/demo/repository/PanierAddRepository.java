package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Paniers;

public interface PanierAddRepository extends JpaRepository<Paniers, Integer> {
    Optional<Paniers> findByIdClient(int idClient);
}