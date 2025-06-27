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
        SELECT p.* 
        FROM produits p
        JOIN categories c ON p.id_categorie = c.id_categorie
        WHERE 
            (:search IS NULL OR p.nom ILIKE '%' || :search || '%')
            AND (:categorie IS NULL OR c.libelle ILIKE '%' || :categorie || '%')
            AND (:prixMin IS NULL OR p.prix_vente >= :prixMin)
            AND (:prixMax IS NULL OR p.prix_vente <= :prixMax)
    """, nativeQuery = true)
    List<Produit> findProduitsByCritere(
            @Param("search") String search,
            @Param("categorie") String categorie,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax
    );
}