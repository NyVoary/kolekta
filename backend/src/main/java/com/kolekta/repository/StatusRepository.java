package com.kolekta.repository;

import com.kolekta.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    java.util.Optional<Status> findByLibelle(String libelle);
}
