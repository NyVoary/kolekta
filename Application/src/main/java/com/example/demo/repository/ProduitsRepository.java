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
public interface ProduitsRepository extends JpaRepository<Produits, Long> { // Changé de Integer à Long

    @Query(value = "SELECT p.* FROM produit_mois pm JOIN produits p on p.id_produit = pm.id_produit WHERE pm.mois = :mois", nativeQuery = true)
    List<Produits> listProduitsSaisonniers(@Param("mois") int mois);

    // Supprimez cette méthode car elle fait doublon avec celle héritée de JpaRepository
    // Optional<Produits> findById(Long id);

    @Query("SELECT p FROM Produits p JOIN p.categorie c WHERE "
            + "(:search IS NULL OR :search = '' OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%'))) "
            + "AND (:categorie IS NULL OR :categorie = '' OR LOWER(c.libelle) LIKE LOWER(CONCAT('%', :categorie, '%'))) "
            + "AND (:prixMin IS NULL OR p.prixVente >= :prixMin) "
            + "AND (:prixMax IS NULL OR p.prixVente <= :prixMax)")
    List<Produits> findProduitsByCritere(
            @Param("search") String search,
            @Param("categorie") String categorie,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax);

    // Alternative avec requête native si la requête JPQL ne fonctionne pas
    @Query(value = "SELECT p.* FROM produits p " +
                   "JOIN categories c ON c.id_categorie = p.id_categorie " +
                   "WHERE (:search IS NULL OR :search = '' OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%'))) " +
                   "AND (:categorie IS NULL OR :categorie = '' OR LOWER(c.libelle) LIKE LOWER(CONCAT('%', :categorie, '%'))) " +
                   "AND (:prixMin IS NULL OR p.prix_vente >= :prixMin) " +
                   "AND (:prixMax IS NULL OR p.prix_vente <= :prixMax)", 
           nativeQuery = true)
    List<Produits> findProduitsByCritereNative(
            @Param("search") String search,
            @Param("categorie") String categorie,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax);
    List<Produits> findByFournisseurs_Id(Long idFournisseur);

    @Query("SELECT COUNT(p.id) FROM Produits p WHERE p.fournisseurs.id = :fournisseurId AND p.date_ajout BETWEEN :dateDebut AND :dateFin")
    Long countProduitsByFournisseurAndDate(@Param("fournisseurId") Long fournisseurId,
            @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT p.fournisseurs.id, COUNT(p.id) FROM Produits p WHERE p.date_ajout BETWEEN :dateDebut AND :dateFin GROUP BY p.fournisseurs.id")
    List<Object[]> countProduitsGroupByFournisseur(@Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}
