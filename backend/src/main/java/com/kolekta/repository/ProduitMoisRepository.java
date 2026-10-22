package com.kolekta.repository;

import com.kolekta.entity.ProduitMois;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitMoisRepository extends JpaRepository<ProduitMois, Long> {
    List<ProduitMois> findByProduitIdProduit(Long idProduit);
    List<ProduitMois> findByMois(Integer mois);
    boolean existsByProduitIdProduitAndMois(Long idProduit, Integer mois);
}
