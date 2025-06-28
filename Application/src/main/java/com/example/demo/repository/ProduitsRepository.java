package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Produits;

public interface ProduitsRepository extends JpaRepository<Produits, Integer> {
}