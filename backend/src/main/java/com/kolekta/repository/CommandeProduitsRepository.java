package com.kolekta.repository;

import com.kolekta.entity.CommandeProduits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeProduitsRepository extends JpaRepository<CommandeProduits, Long> {
    List<CommandeProduits> findByCommandeIdCommande(Long idCommande);
}
