package com.example.demo.repository;

import com.example.demo.entity.CommandeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommandeStatusRepository extends JpaRepository<CommandeStatus, Long> {
    
    Optional<CommandeStatus> findTopByCommandeIdOrderByDateChangementDesc(Long commandeId);
    
    List<CommandeStatus> findByCommandeIdOrderByDateChangementDesc(Long commandeId);
    List<CommandeStatus> findByCommandeIdCommande(Long idCommande);
}