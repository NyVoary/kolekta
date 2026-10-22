package com.kolekta.service;

import com.kolekta.dto.tache.TacheRequest;
import com.kolekta.dto.tache.TacheResponse;

import java.util.List;

public interface TacheService {
    TacheResponse creer(TacheRequest request);
    TacheResponse modifier(Long id, TacheRequest request);
    TacheResponse trouverParId(Long id);
    List<TacheResponse> listerParEmploye(Long idEmploye);
    List<TacheResponse> listerTous();
    void changerStatus(Long id, Long idStatusTache, Long idEmploye);
}
