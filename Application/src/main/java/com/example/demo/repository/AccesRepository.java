package com.example.demo.repository;

import com.example.demo.entity.Acces;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccesRepository extends JpaRepository<Acces, Long> {
    Optional<Acces> findByNomUtilisateur(String nomUtilisateur);
}
