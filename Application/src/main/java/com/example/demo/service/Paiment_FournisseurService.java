package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Paiment_Fournisseur;
import com.example.demo.repository.Paiment_FournisseurRepository;

@Service
public class Paiment_FournisseurService {

    @Autowired
    private Paiment_FournisseurRepository paiementRepository;

    public List<Paiment_Fournisseur> getTousLesPaiements() {
        return paiementRepository.findAll();
    }

    public List<Paiment_Fournisseur> rechercherPaiements(LocalDate dateDebut, LocalDate dateFin, String nomFournisseur) {
        // Gestion des paramètres optionnels
        LocalDate dateDebutParam = dateDebut;
        LocalDate dateFinParam = dateFin;
        String nomFournisseurParam = (nomFournisseur != null && nomFournisseur.isEmpty()) ? null : nomFournisseur;

        return paiementRepository.findPaiementsByCriteria(dateDebutParam, dateFinParam, nomFournisseurParam);
    }
}