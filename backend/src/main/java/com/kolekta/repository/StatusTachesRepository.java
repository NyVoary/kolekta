package com.kolekta.repository;

import com.kolekta.entity.StatusTaches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTachesRepository extends JpaRepository<StatusTaches, Long> {
    java.util.Optional<StatusTaches> findByLibelle(String libelle);
}
