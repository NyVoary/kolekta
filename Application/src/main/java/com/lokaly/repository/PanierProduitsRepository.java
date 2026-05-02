package com.lokaly.repository;

import com.lokaly.entity.PanierProduits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PanierProduitsRepository extends JpaRepository<PanierProduits, Long> {
    List<PanierProduits> findByPanierIdPanier(Long idPanier);
    Optional<PanierProduits> findByPanierIdPanierAndProduitIdProduit(Long idPanier, Long idProduit);
    void deleteByPanierIdPanierAndProduitIdProduit(Long idPanier, Long idProduit);
}
