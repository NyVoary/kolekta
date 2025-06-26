package com.example.demo.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.admin.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
}