package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Fournisseurs;

@Repository
public interface FornisseursRepository extends JpaRepository<Fournisseurs, Long> {}
