package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client enregistrerClient(Client client) {
        return clientRepository.save(client);
    }

    public void supprimerClient(Long id) {
        clientRepository.deleteById(id);
    }

    public boolean existeClient(Long id) {
        return clientRepository.existsById(id);
    }
}
