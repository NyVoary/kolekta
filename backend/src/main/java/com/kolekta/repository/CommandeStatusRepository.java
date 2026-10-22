package com.kolekta.repository;

import com.kolekta.entity.CommandeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeStatusRepository extends JpaRepository<CommandeStatus, Long> {
    List<CommandeStatus> findByCommandeIdCommandeOrderByDateChangementDesc(Long idCommande);
}
