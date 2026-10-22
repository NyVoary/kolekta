package com.lokaly.service;

import com.lokaly.dto.fournisseur.FournisseurRequest;
import com.lokaly.dto.fournisseur.FournisseurResponse;

import java.util.List;

public interface FournisseurService {
    FournisseurResponse creer(FournisseurRequest request);
    FournisseurResponse modifier(Long id, FournisseurRequest request);
    FournisseurResponse trouverParId(Long id);
    List<FournisseurResponse> listerActifs();
    void desactiver(Long id);
}
