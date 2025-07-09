package com.example.demo.repository;

import com.example.demo.entity.Client;
import com.example.demo.entity.ClientStatistique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientStatistiqueRepository extends JpaRepository<Client, Integer> {

       // Statistiques générales (toutes dates)
       @Query("SELECT COUNT(c) FROM Client c")
       Long countAllClients();

       @Query("SELECT COUNT(co) FROM Commandes co")
       Long countAllCommandes();

       @Query("SELECT COALESCE(SUM(co.montantTotal), 0) FROM Commandes co")
       Double getGeneralChiffreAffairesTotal();

       default ClientStatistique getGeneralStats() {
              Long nbClients = countAllClients();
              Long nbCommandes = countAllCommandes();
              Double caTotal = getGeneralChiffreAffairesTotal();
              Double panierMoyen = nbCommandes > 0 ? caTotal / nbCommandes : 0.0;

              return new ClientStatistique("Résumé général", nbClients, caTotal, nbCommandes, panierMoyen);
       }

       // Statistiques résumées entre dates
       default ClientStatistique getResumeStats(LocalDate debut, LocalDate fin) {
              Long nbClients = countNewClients(debut, fin);
              Long nbCommandes = countCommandes(debut, fin);
              Double caTotal = getChiffreAffairesTotal(debut, fin);
              Double panierMoyen = nbCommandes > 0 ? caTotal / nbCommandes : 0.0;

              return new ClientStatistique("Résumé", nbClients, caTotal, nbCommandes, panierMoyen);
       }

       // Statistiques générales par ville
       @Query("SELECT new com.example.demo.entity.ClientStatistique(" +
                     "co.villeLivraison, COUNT(co), SUM(co.montantTotal)) " +
                     "FROM Commandes co " +
                     "GROUP BY co.villeLivraison " +
                     "ORDER BY COUNT(co) DESC")
       List<ClientStatistique> getGeneralStatsByVille();

       // Statistiques générales par sexe
       @Query("SELECT new com.example.demo.entity.ClientStatistique(" +
                     "c.sexe, " + // sexe vaut maintenant 'Masculin', 'Féminin' ou 'Autre'
                     "COUNT(c), null) " +
                     "FROM Client c " +
                     "GROUP BY c.sexe")
       List<ClientStatistique> getGeneralStatsBySexe();

       // Top produits général
       @Query("SELECT new com.example.demo.entity.ClientStatistique(" +
                     "p.nom, SUM(cp.quantite), SUM(cp.quantite * cp.prixUnitaire)) " +
                     "FROM CommandeProduits cp " +
                     "JOIN cp.produit p " +
                     "JOIN cp.commande c " +
                     "GROUP BY p.nom " +
                     "ORDER BY SUM(cp.quantite) DESC")
       List<ClientStatistique> getGeneralTopProduits();

       // Méthodes existantes pour les stats entre dates...
       @Query("SELECT COUNT(c) FROM Client c WHERE c.dateCreation BETWEEN :debut AND :fin")
       Long countNewClients(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

       @Query("SELECT COUNT(c) FROM Commandes c WHERE c.dateCommande BETWEEN :debut AND :fin")
       Long countCommandes(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

       @Query("SELECT COALESCE(SUM(c.montantTotal), 0) FROM Commandes c WHERE c.dateCommande BETWEEN :debut AND :fin")
       Double getChiffreAffairesTotal(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

       @Query("SELECT new com.example.demo.entity.ClientStatistique(" +
                     "co.villeLivraison, COUNT(co), SUM(co.montantTotal)) " +
                     "FROM Commandes co " +
                     "WHERE co.dateCommande BETWEEN :debut AND :fin " +
                     "GROUP BY co.villeLivraison " +
                     "ORDER BY COUNT(co) DESC")
       List<ClientStatistique> getStatsByVille(@Param("debut") LocalDate debut,
                     @Param("fin") LocalDate fin);

       @Query("SELECT new com.example.demo.entity.ClientStatistique(" +
                     "c.sexe, " +
                     "COUNT(c), null) " +
                     "FROM Client c " +
                     "WHERE c.dateCreation BETWEEN :debut AND :fin " +
                     "GROUP BY c.sexe")
       List<ClientStatistique> getStatsBySexe(@Param("debut") LocalDate debut,
                     @Param("fin") LocalDate fin);

       @Query("SELECT new com.example.demo.entity.ClientStatistique(" +
                     "p.nom, SUM(cp.quantite), SUM(cp.quantite * cp.prixUnitaire)) " +
                     "FROM CommandeProduits cp " +
                     "JOIN cp.produit p " +
                     "JOIN cp.commande c " +
                     "WHERE c.dateCommande BETWEEN :debut AND :fin " +
                     "GROUP BY p.nom " +
                     "ORDER BY SUM(cp.quantite) DESC")
       List<ClientStatistique> getTopProduits(@Param("debut") LocalDate debut,
                     @Param("fin") LocalDate fin);
}