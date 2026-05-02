package com.lokaly.repository;

import com.lokaly.entity.StatusPaiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusPaiementRepository extends JpaRepository<StatusPaiement, Integer> {
}
