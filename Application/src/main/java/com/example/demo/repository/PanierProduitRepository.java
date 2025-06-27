package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PanierProduit;

@Repository
public interface PanierProduitRepository extends JpaRepository<PanierProduit, Long> {

    List<PanierProduit> findByPanierIdPanier(Long idPanier);

    Optional<PanierProduit> findByPanierIdPanierAndProduitIdProduit(Long idPanier, Long idProduit);

    @Modifying
    @Transactional
    @Query("DELETE FROM PanierProduit pp WHERE pp.panier.idPanier = :idPanier AND pp.produit.idProduit = :idProduit")
    void deleteByPanierIdAndProduitId(@Param("idPanier") Long idPanier, @Param("idProduit") Long idProduit);

    @Query("SELECT COUNT(pp) FROM PanierProduit pp WHERE pp.panier.idPanier = :idPanier")
    Long countByPanierId(@Param("idPanier") Long idPanier);

    @Modifying
    @Query("DELETE FROM PanierProduit pp WHERE pp.panier.idPanier = :idPanier")
    void deleteByPanierId(@Param("idPanier") Long idPanier);
}
