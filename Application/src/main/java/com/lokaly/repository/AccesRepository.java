package com.lokaly.repository;

import com.lokaly.entity.Acces;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccesRepository extends JpaRepository<Acces, Long> {
    Optional<Acces> findByLogin(String login);
    boolean existsByLogin(String login);
}
