package com.lokaly.repository;

import com.lokaly.entity.Parametre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParametreRepository extends JpaRepository<Parametre, Long> {
    Optional<Parametre> findByCle(String cle);
    boolean existsByCle(String cle);
}
