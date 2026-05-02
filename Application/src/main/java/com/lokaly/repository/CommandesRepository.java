package com.lokaly.repository;

import com.lokaly.entity.Commandes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandesRepository extends JpaRepository<Commandes, Long> {
    List<Commandes> findByClientIdClient(Long idClient);
    List<Commandes> findByStatusLibelle(String libelle);
    List<Commandes> findByEmployeIdEmploye(Long idEmploye);
}
