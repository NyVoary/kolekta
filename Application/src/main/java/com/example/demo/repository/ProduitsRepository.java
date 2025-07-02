package com.example.demo.repository;

import java.time.LocalDate;
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

    @Query("SELECT COUNT(p.id) FROM Produits p WHERE p.fournisseurs.id = :fournisseurId AND p.date_ajout BETWEEN :dateDebut AND :dateFin")
    Long countProduitsByFournisseurAndDate(@Param("fournisseurId") Long fournisseurId,
            @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT p.fournisseurs.id, COUNT(p.id) FROM Produits p WHERE p.date_ajout BETWEEN :dateDebut AND :dateFin GROUP BY p.fournisseurs.id")
    List<Object[]> countProduitsGroupByFournisseur(@Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}
