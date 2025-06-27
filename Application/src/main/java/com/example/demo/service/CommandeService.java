package com.example.demo.service;

import com.example.demo.entity.Commande;
import com.example.demo.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;

    @Transactional(readOnly = true)
    public Commande getCommandeWithProduits(Long id) {
        Commande commande = commandeRepository.findById(id).orElse(null);
        if (commande != null) {
            commande.getProduits().size();
        }
        return commande;
    }
}