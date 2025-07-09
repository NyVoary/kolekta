package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public List<Paiment_Fournisseur> rechercherPaiements(String dateDebutStr, String dateFinStr, String nomFournisseur) {
        LocalDate dateDebut = null;
        LocalDate dateFin = null;
        
        try {
            dateDebut = dateDebutStr != null && !dateDebutStr.isEmpty() ? 
                LocalDate.parse(dateDebutStr) : null;
            dateFin = dateFinStr != null && !dateFinStr.isEmpty() ? 
                LocalDate.parse(dateFinStr) : null;
        } catch (DateTimeParseException e) {
            // Gérer l'erreur de format de date si nécessaire
        }
        
        String nom = nomFournisseur != null && nomFournisseur.isEmpty() ? null : nomFournisseur;
        
        return paiementRepository.findPaiementsByCriteria(dateDebut, dateFin, nom);
    }
}