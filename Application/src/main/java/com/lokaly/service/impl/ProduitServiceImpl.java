package com.lokaly.service.impl;

import com.lokaly.dto.produit.ProduitRequest;
import com.lokaly.dto.produit.ProduitResponse;
import com.lokaly.entity.*;
import com.lokaly.exception.ResourceNotFoundException;
import com.lokaly.repository.*;
import com.lokaly.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitsRepository produitsRepository;
    private final CategoriesRepository categoriesRepository;
    private final FournisseursRepository fournisseursRepository;
    private final ProduitMoisRepository produitMoisRepository;

    @Override
    @Transactional
    public ProduitResponse creer(ProduitRequest request) {
        Produits p = buildFromRequest(new Produits(), request);
        Produits saved = produitsRepository.save(p);
        saveMois(saved, request.getMoisDisponibles());
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ProduitResponse modifier(Long id, ProduitRequest request) {
        Produits p = produitsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", id));
        buildFromRequest(p, request);
        Produits saved = produitsRepository.save(p);
        produitMoisRepository.deleteAll(produitMoisRepository.findByProduitIdProduit(id));
        saveMois(saved, request.getMoisDisponibles());
        return toResponse(saved);
    }

    @Override
    public ProduitResponse trouverParId(Long id) {
        return toResponse(produitsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", id)));
    }

    @Override
    public List<ProduitResponse> listerTous() {
        return produitsRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<ProduitResponse> listerParCategorie(Long idCategorie) {
        return produitsRepository.findByCategorieIdCategorie(idCategorie).stream().map(this::toResponse).toList();
    }

    @Override
    public List<ProduitResponse> rechercher(String nom) {
        return produitsRepository.findByNomContainingIgnoreCase(nom).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!produitsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit", id);
        }
        produitMoisRepository.deleteAll(produitMoisRepository.findByProduitIdProduit(id));
        produitsRepository.deleteById(id);
    }

    private Produits buildFromRequest(Produits p, ProduitRequest request) {
        Categories categorie = categoriesRepository.findById(request.getIdCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", request.getIdCategorie()));

        Fournisseurs fournisseur = null;
        if (request.getIdFournisseur() != null) {
            fournisseur = fournisseursRepository.findById(request.getIdFournisseur())
                    .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", request.getIdFournisseur()));
        }

        p.setNom(request.getNom());
        p.setDescription(request.getDescription());
        p.setCategorie(categorie);
        p.setFournisseur(fournisseur);
        p.setUniteMesure(request.getUniteMesure());
        p.setPrixAchat(request.getPrixAchat());
        p.setPrixVente(request.getPrixVente());
        p.setImage(request.getImage());
        return p;
    }

    private void saveMois(Produits produit, List<Integer> moisList) {
        if (moisList == null) return;
        for (Integer mois : moisList) {
            ProduitMois pm = new ProduitMois();
            pm.setProduit(produit);
            pm.setMois(mois);
            produitMoisRepository.save(pm);
        }
    }

    private ProduitResponse toResponse(Produits p) {
        List<Integer> mois = produitMoisRepository.findByProduitIdProduit(p.getIdProduit())
                .stream().map(ProduitMois::getMois).toList();

        return ProduitResponse.builder()
                .idProduit(p.getIdProduit())
                .nom(p.getNom())
                .description(p.getDescription())
                .categorie(p.getCategorie() != null ? p.getCategorie().getLibelle() : null)
                .idCategorie(p.getCategorie() != null ? p.getCategorie().getIdCategorie() : null)
                .fournisseur(p.getFournisseur() != null ? p.getFournisseur().getNom() : null)
                .idFournisseur(p.getFournisseur() != null ? p.getFournisseur().getIdFournisseur() : null)
                .uniteMesure(p.getUniteMesure())
                .prixAchat(p.getPrixAchat())
                .prixVente(p.getPrixVente())
                .image(p.getImage())
                .dateAjout(p.getDateAjout())
                .moisDisponibles(mois)
                .build();
    }
}
