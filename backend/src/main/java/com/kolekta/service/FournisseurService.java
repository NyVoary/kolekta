package com.kolekta.service;

import com.kolekta.dto.fournisseur.FournisseurRequest;
import com.kolekta.dto.fournisseur.FournisseurResponse;

import java.util.List;

public interface FournisseurService {
    FournisseurResponse creer(FournisseurRequest request);
    FournisseurResponse modifier(Long id, FournisseurRequest request);
    FournisseurResponse trouverParId(Long id);
    List<FournisseurResponse> listerActifs();
    void desactiver(Long id);
}
