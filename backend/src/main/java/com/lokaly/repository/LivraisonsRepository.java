package com.lokaly.repository;

import com.lokaly.entity.Livraisons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivraisonsRepository extends JpaRepository<Livraisons, Long> {
    List<Livraisons> findByCommandeIdCommande(Long idCommande);
    List<Livraisons> findByStatusLibelle(String libelle);
}
