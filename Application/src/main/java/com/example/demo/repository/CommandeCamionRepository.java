package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.CommandeCamion;
import java.util.Optional;

public interface CommandeCamionRepository extends JpaRepository<CommandeCamion, Long> {
    Optional<CommandeCamion> findByCommandeIdCommande(Long idCommande);
}