package com.kolekta.repository;

import com.kolekta.entity.PaiementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementStatusRepository extends JpaRepository<PaiementStatus, Long> {
    List<PaiementStatus> findByCommandePaiementIdCommandePaiementOrderByDateChangementDesc(Long idCommandePaiement);
}
