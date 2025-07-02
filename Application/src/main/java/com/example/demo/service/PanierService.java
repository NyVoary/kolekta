package com.example.demo.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Panier;
import com.example.demo.entity.*;
import com.example.demo.entity.Produits;
import com.example.demo.entity.PanierProduits;
import com.example.demo.repository.PanierProduitRepository;
import com.example.demo.repository.PanierRepository;
import com.example.demo.repository.ProduitRepository;

@Service
@Transactional
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private PanierProduitRepository panierProduitRepository;

    @Autowired
    private ProduitRepository produitRepository;

    private static final Double TAUX_TVA = 20.0;
    private static final Double FRAIS_LIVRAISON = 10000.0;

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
        return montantHT * 0.20;
    }

    public Double calculerMontantTTC(Double montantHT) {
        return montantHT + calculerTVA(montantHT);
    }

    public Double calculerMontantTotal(Double montantTTC) {
        return montantTTC + 5000.0;
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

    public Double getFraisLivraison() {
        return FRAIS_LIVRAISON;
    }
}