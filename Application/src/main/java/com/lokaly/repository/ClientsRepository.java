package com.lokaly.repository;

import com.lokaly.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Optional<Clients> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
}
