package com.kolekta.service;

import com.kolekta.dto.commande.CommandeRequest;
import com.kolekta.dto.commande.CommandeResponse;

import java.util.List;

public interface CommandeService {
    CommandeResponse creer(CommandeRequest request);
    CommandeResponse trouverParId(Long id);
    List<CommandeResponse> listerParClient(Long idClient);
    List<CommandeResponse> listerTous();
    void changerStatus(Long id, Long idStatus, Long idEmploye);
}
