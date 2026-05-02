package com.lokaly.repository;

import com.lokaly.entity.CommandePaiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandePaiementRepository extends JpaRepository<CommandePaiement, Long> {
    List<CommandePaiement> findByCommandeIdCommande(Long idCommande);
}
