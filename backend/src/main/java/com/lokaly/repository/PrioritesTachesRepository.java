package com.lokaly.repository;

import com.lokaly.entity.PrioritesTaches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrioritesTachesRepository extends JpaRepository<PrioritesTaches, Long> {
    java.util.Optional<PrioritesTaches> findByLibelle(String libelle);
}
