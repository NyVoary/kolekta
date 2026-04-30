package com.example.demo.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

@Service
@Transactional
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private ParametreRepository paraRepository;

    @Autowired
    private PanierProduitRepository panierProduitRepository;

    @Autowired
    private ProduitsRepository produitRepository;

    private static final Double TAUX_TVA = 20.0;

    public Panier creerPanierPourClient(Long idClient) {
        if (panierRepository.existsByIdClient(idClient)) {
            return panierRepository.findByIdClient(idClient).orElse(null);
        }

        Panier nouveauPanier = new Panier(idClient, LocalDate.now());
        return panierRepository.save(nouveauPanier);
    }

    public Optional<Panier> getPanierAvecProduits(Long idClient) {
        return panierRepository.findByIdClientWithProduits(idClient);
    }

    public void ajouterProduitAuPanier(Long idClient, Long idProduit, Integer quantite) {
        Panier panier = panierRepository.findByIdClient(idClient)
                .orElseGet(() -> creerPanierPourClient(idClient));

        Optional<Produits> produitOpt = produitRepository.findById(idProduit);
        if (!produitOpt.isPresent()) {
            throw new RuntimeException("Produit non trouvé");
        }

        Produits produit = produitOpt.get();

        Optional<PanierProduits> panierProduitExistant = panierProduitRepository
                .findByPanierIdPanierAndProduitId(panier.getIdPanier(), idProduit);

        if (panierProduitExistant.isPresent()) {

            PanierProduits pp = panierProduitExistant.get();
            pp.setQuantite(pp.getQuantite() + quantite);
            panierProduitRepository.save(pp);
        } else {

            PanierProduits nouveauPanierProduit = new PanierProduits(
                    panier, produit, quantite.longValue(), Double.valueOf(produit.getPrixVente()));
            panierProduitRepository.save(nouveauPanierProduit);
        }
    }

    public void mettreAJourQuantite(Long idClient, Long idProduit, Integer nouvelleQuantite) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (!panierOpt.isPresent()) {
            throw new RuntimeException("Panier non trouvé");
        }

        Optional<PanierProduits> panierProduitOpt = panierProduitRepository.findByPanierIdPanierAndProduitId(
                panierOpt.get().getIdPanier(), idProduit);

        if (panierProduitOpt.isPresent()) {
            if (nouvelleQuantite <= 0) {
                supprimerProduitDuPanier(idClient, idProduit);
            } else {
                PanierProduits pp = panierProduitOpt.get();
                pp.setQuantite(nouvelleQuantite.longValue());
                panierProduitRepository.save(pp);
            }
        }
    }

    public void supprimerProduitDuPanier(Long idClient, Long idProduit) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (panierOpt.isPresent()) {
            panierProduitRepository.deleteByPanierIdAndProduitId(
                    panierOpt.get().getIdPanier(), idProduit);
        }
    }

    public Double calculerMontantHT(Panier panier) {
        return panier.getPanierProduits().stream()
                .map(PanierProduits::getSousTotal)
                .filter(Objects::nonNull)
                .reduce(0.0, Double::sum);
    }

    public Double calculerTVA(Double montantHT) {
    Parametre tva = paraRepository.findByLibelle("TVA");
    // Si tva est null, on applique 20% par défaut (0.20 en décimal)
    double tva_val = tva != null ? tva.getValeur() / 100.0 : 0.20;
    return montantHT * tva_val;
}


    public Double calculerMontantTTC(Double montantHT) {
        return montantHT + calculerTVA(montantHT);
    }

    public Double calculerMontantTotal(Double montantHT) {
        return calculerMontantTTC(montantHT) + getFraisLivraison(montantHT);
    }

    public void viderPanier(Long idClient) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (panierOpt.isPresent()) {
            Long idPanier = panierOpt.get().getIdPanier();
            panierProduitRepository.deleteByPanierId(idPanier);
        }
    }

    public Long compterArticlesPanier(Long idClient) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (panierOpt.isPresent()) {
            return panierProduitRepository.countByPanierId(panierOpt.get().getIdPanier());
        }
        return 0L;
    }

    // public Double getFraisLivraison(Double montantHT) {
    // if (montantHT*0.10 < 5000) {
    // return (double)5000;
    // }
    // return montantHT*0.10;
    // }
    public Double getFraisLivraison(Double montantHT) {
        Parametre minParam = paraRepository.findByLibelle("min_livraison");
        Parametre livraisonParam = paraRepository.findByLibelle("livraison");
        double min = minParam != null ? minParam.getValeur() : 5000; // valeur par défaut 5000
        double livraisonPourcentage = livraisonParam != null ? livraisonParam.getValeur() / 100.0 : 0.10; // valeur par
                                                                                                          // défaut 0.10
        double frais = montantHT * livraisonPourcentage;
        if (frais < min) {
            return min;
        }
        return frais;
    }
}