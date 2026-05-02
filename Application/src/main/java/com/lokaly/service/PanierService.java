package com.lokaly.service;

import com.lokaly.dto.panier.AjoutPanierRequest;
import com.lokaly.dto.panier.PanierResponse;

public interface PanierService {
    PanierResponse obtenirPanierActif(Long idClient);
    PanierResponse ajouterProduit(Long idClient, AjoutPanierRequest request);
    PanierResponse modifierQuantite(Long idClient, Long idProduit, Integer quantite);
    void supprimerProduit(Long idClient, Long idProduit);
    void viderPanier(Long idClient);
}
