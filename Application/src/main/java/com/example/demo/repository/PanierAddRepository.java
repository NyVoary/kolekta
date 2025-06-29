package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Panier;

public interface PanierAddRepository extends JpaRepository<Panier, Long> {
    Optional<Panier> findByIdClient(Long idClient);
}