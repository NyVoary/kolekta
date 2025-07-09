package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class Partage {
    private Long idCommande;

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Long getIdCommande() {
        return idCommande;
    }
}

