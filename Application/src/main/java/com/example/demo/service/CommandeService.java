package com.example.demo.service;

import com.example.demo.entity.Commande;
import com.example.demo.entity.Client;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.repository.CommandeStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommandeService {
    private CommandeRepository commandeRepository;
    private CommandeStatusRepository commandeStatusRepository;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository, 
                         CommandeStatusRepository commandeStatusRepository) {
        this.commandeRepository = commandeRepository;
        this.commandeStatusRepository = commandeStatusRepository;
    }
    public List<Commande> getCommandesByClient(Client client) {
        return commandeRepository.findByClientOrderByDateCommandeDesc(client);
    }

    public List<Commande> getCommandesWithProduitsByClientId(int clientId) {
        return commandeRepository.findWithProduitsByClientId(clientId);
    }

    public List<Commande> getAllCommandesByClientWithProduits(int clientId) {
        return commandeRepository.findAllByClientIdWithProduits(clientId);
    }

    public String getDernierStatutCommande(Long commandeId) {
        return commandeStatusRepository.findTopByCommandeIdOrderByDateChangementDesc(commandeId)
                .map(cs -> cs.getStatus().getLibelle())
                .orElse("Inconnu");
    }
}