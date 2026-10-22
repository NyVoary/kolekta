package com.kolekta.service;

import com.kolekta.dto.client.ClientRequest;
import com.kolekta.dto.client.ClientResponse;

import java.util.List;

public interface ClientService {
    ClientResponse inscrire(ClientRequest request);
    ClientResponse modifier(Long id, ClientRequest request);
    ClientResponse trouverParId(Long id);
    List<ClientResponse> listerActifs();
    void desactiver(Long id);
}
