package com.kolekta.service;

import com.kolekta.dto.produit.ProduitRequest;
import com.kolekta.dto.produit.ProduitResponse;

import java.util.List;

public interface ProduitService {
    ProduitResponse creer(ProduitRequest request);
    ProduitResponse modifier(Long id, ProduitRequest request);
    ProduitResponse trouverParId(Long id);
    List<ProduitResponse> listerTous();
    List<ProduitResponse> listerParCategorie(Long idCategorie);
    List<ProduitResponse> rechercher(String nom);
    void supprimer(Long id);
}
