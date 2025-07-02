package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Produits;

@Repository
public interface ProduitsRepository extends JpaRepository<Produits, Integer> {

    @Query(value = "SELECT p.* FROM produit_mois pm JOIN produits p on p.id_produit = pm.id_produit WHERE pm.mois = :mois", nativeQuery = true)
    List<Produits> listProduitsSaisonniers(@Param("mois") int mois);

    List<Produits> findByFournisseurs_Id(Long idFournisseur);
}
