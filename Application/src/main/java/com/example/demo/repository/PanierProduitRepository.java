package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PanierProduits;

@Repository
public interface PanierProduitRepository extends JpaRepository<PanierProduits, Long> {

    List<PanierProduits> findByPanierIdPanier(Long idPanier);

    Optional<PanierProduits> findByPanierIdPanierAndProduitId(Long idPanier, Long idProduit);

    @Modifying
    @Transactional
    @Query("DELETE FROM PanierProduits pp WHERE pp.panier.id = :idPanier AND pp.produit.id = :idProduit")
    void deleteByPanierIdAndProduitId(@Param("idPanier") Long idPanier, @Param("idProduit") Long idProduit);

    @Query("SELECT COUNT(pp) FROM PanierProduits pp WHERE pp.panier.id = :idPanier")
    Long countByPanierId(@Param("idPanier") Long idPanier);

    @Modifying
    @Transactional
    @Query("DELETE FROM PanierProduits pp WHERE pp.panier.id = :idPanier")
    void deleteByPanierId(@Param("idPanier") Long idPanier);
}
