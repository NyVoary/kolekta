package com.kolekta.service.impl;

import com.kolekta.dto.panier.AjoutPanierRequest;
import com.kolekta.dto.panier.PanierResponse;
import com.kolekta.entity.*;
import com.kolekta.exception.BadRequestException;
import com.kolekta.exception.ResourceNotFoundException;
import com.kolekta.repository.*;
import com.kolekta.service.PanierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PanierServiceImpl implements PanierService {

    private final PanierRepository panierRepository;
    private final PanierProduitsRepository panierProduitsRepository;
    private final ClientsRepository clientsRepository;
    private final ProduitsRepository produitsRepository;

    @Override
    public PanierResponse obtenirPanierActif(Long idClient) {
        Panier panier = getOrCreatePanier(idClient);
        return toResponse(panier);
    }

    @Override
    @Transactional
    public PanierResponse ajouterProduit(Long idClient, AjoutPanierRequest request) {
        Panier panier = getOrCreatePanier(idClient);
        Produits produit = produitsRepository.findById(request.getIdProduit())
                .orElseThrow(() -> new ResourceNotFoundException("Produit", request.getIdProduit()));

        PanierProduits ligne = panierProduitsRepository
                .findByPanierIdPanierAndProduitIdProduit(panier.getIdPanier(), produit.getIdProduit())
                .orElse(null);

        if (ligne == null) {
            ligne = new PanierProduits();
            ligne.setPanier(panier);
            ligne.setProduit(produit);
            ligne.setQuantite(request.getQuantite());
        } else {
            ligne.setQuantite(ligne.getQuantite() + request.getQuantite());
        }
        panierProduitsRepository.save(ligne);

        return toResponse(panier);
    }

    @Override
    @Transactional
    public PanierResponse modifierQuantite(Long idClient, Long idProduit, Integer quantite) {
        if (quantite <= 0) {
            throw new BadRequestException("La quantité doit être supérieure à 0");
        }
        Panier panier = getOrCreatePanier(idClient);
        PanierProduits ligne = panierProduitsRepository
                .findByPanierIdPanierAndProduitIdProduit(panier.getIdPanier(), idProduit)
                .orElseThrow(() -> new ResourceNotFoundException("Produit dans le panier", idProduit));
        ligne.setQuantite(quantite);
        panierProduitsRepository.save(ligne);
        return toResponse(panier);
    }

    @Override
    @Transactional
    public void supprimerProduit(Long idClient, Long idProduit) {
        Panier panier = getOrCreatePanier(idClient);
        panierProduitsRepository.deleteByPanierIdPanierAndProduitIdProduit(panier.getIdPanier(), idProduit);
    }

    @Override
    @Transactional
    public void viderPanier(Long idClient) {
        Panier panier = getOrCreatePanier(idClient);
        panierProduitsRepository.deleteAll(panierProduitsRepository.findByPanierIdPanier(panier.getIdPanier()));
    }

    private Panier getOrCreatePanier(Long idClient) {
        return panierRepository.findTopByClientIdClientOrderByDateCreationDesc(idClient)
                .orElseGet(() -> {
                    Clients client = clientsRepository.findById(idClient)
                            .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
                    Panier p = new Panier();
                    p.setClient(client);
                    return panierRepository.save(p);
                });
    }

    private PanierResponse toResponse(Panier panier) {
        List<PanierProduits> lignes = panierProduitsRepository.findByPanierIdPanier(panier.getIdPanier());

        BigDecimal total = lignes.stream()
                .map(l -> l.getProduit().getPrixVente().multiply(BigDecimal.valueOf(l.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<PanierResponse.LignePanierResponse> items = lignes.stream()
                .map(l -> PanierResponse.LignePanierResponse.builder()
                        .idPanierProduit(l.getIdPanierProduit())
                        .idProduit(l.getProduit().getIdProduit())
                        .nomProduit(l.getProduit().getNom())
                        .image(l.getProduit().getImage())
                        .prixVente(l.getProduit().getPrixVente())
                        .quantite(l.getQuantite())
                        .sousTotal(l.getProduit().getPrixVente().multiply(BigDecimal.valueOf(l.getQuantite())))
                        .build())
                .toList();

        return PanierResponse.builder()
                .idPanier(panier.getIdPanier())
                .idClient(panier.getClient().getIdClient())
                .dateCreation(panier.getDateCreation())
                .produits(items)
                .total(total)
                .build();
    }
}
