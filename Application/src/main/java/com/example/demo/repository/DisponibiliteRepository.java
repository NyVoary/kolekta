package com.example.demo.repository;

import com.example.demo.entity.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
}
