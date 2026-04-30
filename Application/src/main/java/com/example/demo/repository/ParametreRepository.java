package com.example.demo.repository;

import com.example.demo.entity.Parametre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParametreRepository extends JpaRepository<Parametre, Integer> {
    Parametre findByLibelle(String libelle);
}
