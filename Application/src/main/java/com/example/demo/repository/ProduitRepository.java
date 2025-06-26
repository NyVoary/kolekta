package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("SELECT DISTINCT p.categorie FROM Produit p")
    List<String> findAllCategories();

    @Query("SELECT p FROM Produit p WHERE " +
       "(:search IS NULL OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
       "(:categorie IS NULL OR p.categorie = :categorie) AND " +
       "(:prixMin IS NULL OR p.prix >= :prixMin) AND " +
       "(:prixMax IS NULL OR p.prix <= :prixMax)")
    List<Produit> findProduitsByCritere(
        @Param("search") String search,
        @Param("categorie") String categorie,
        @Param("prixMin") Double prixMin,
        @Param("prixMax") Double prixMax
    );
}