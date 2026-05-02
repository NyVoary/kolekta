package com.lokaly.repository;

import com.lokaly.entity.Camions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CamionsRepository extends JpaRepository<Camions, Long> {
    Optional<Camions> findByImmatriculation(String immatriculation);
    boolean existsByImmatriculation(String immatriculation);
    List<Camions> findByStatusCode(String code);
}
