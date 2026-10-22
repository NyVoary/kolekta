package com.kolekta.repository;

import com.kolekta.entity.CommandesCamions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandesCamionsRepository extends JpaRepository<CommandesCamions, Long> {
    List<CommandesCamions> findByCommandeIdCommande(Long idCommande);
    List<CommandesCamions> findByAssignationCamionIdAssignationCamion(Long idAssignationCamion);
}
