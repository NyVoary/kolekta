package com.lokaly.repository;

import com.lokaly.entity.StatusTaches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTachesRepository extends JpaRepository<StatusTaches, Long> {
    java.util.Optional<StatusTaches> findByLibelle(String libelle);
}
