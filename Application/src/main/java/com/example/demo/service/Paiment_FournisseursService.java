package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Paiment_Fournisseurs;
import com.example.demo.repository.Paiment_FournisseursRepository;

@Service
public class Paiment_FournisseursService {

    @Autowired
    private Paiment_FournisseursRepository paiementRepository;

    public List<Paiment_Fournisseurs> getTousLesPaiements() {
        return paiementRepository.findAll();
    }

    public List<Paiment_Fournisseurs> rechercherPaiements(String dateDebutStr, String dateFinStr, String nomFournisseur) {
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