package com.example.demo.repository;

import com.example.demo.entity.CommandeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeStatusRepository extends JpaRepository<CommandeStatus, Long> {
    List<CommandeStatus> findByCommandeIdCommande(Long idCommande);
}