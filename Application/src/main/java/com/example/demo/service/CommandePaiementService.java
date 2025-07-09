package com.example.demo.service;

import com.example.demo.entity.CommandePaiement;
import com.example.demo.repository.CommandePaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandePaiementService {

    @Autowired
    private CommandePaiementRepository repository;

    public CommandePaiement save(CommandePaiement paiement) {
        return repository.save(paiement);
    }
}
