package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query(value = """
        SELECT p FROM Produit p
        JOIN p.categorie c
        WHERE 
            (:search IS NULL OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:categorie IS NULL OR LOWER(c.libelle) LIKE LOWER(CONCAT('%', :categorie, '%')))
            AND (
                (:prixMin IS NULL AND :prixMax IS NULL)
                OR (:prixMin IS NULL AND p.prixVente <= :prixMax)
                OR (:prixMax IS NULL AND p.prixVente >= :prixMin)
                OR (p.prixVente BETWEEN :prixMin AND :prixMax)
            )
    """, nativeQuery = true)
    List<Produit> findProduitsByCritere(
        @Param("search") String search,
        @Param("categorie") String categorie,
        @Param("prixMin") Double prixMin,
        @Param("prixMax") Double prixMax
    );
}