package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Paiment_Camions;
import com.example.demo.repository.Paiment_CamionsRepository;

@Service
public class Paiment_CamionsService {

    @Autowired
    private Paiment_CamionsRepository paiementRepository;

    public List<Paiment_Camions> getTousLesPaiements() {
        return paiementRepository.findAll();
    }

    public List<Paiment_Camions> rechercherPaiements(String dateDebutStr, String dateFinStr, String immatriculation) {
        LocalDateTime dateDebut = null;
        LocalDateTime dateFin = null;
        
        try {
            dateDebut = dateDebutStr != null && !dateDebutStr.isEmpty() ? 
                LocalDateTime.parse(dateDebutStr) : null;
            dateFin = dateFinStr != null && !dateFinStr.isEmpty() ? 
                LocalDateTime.parse(dateFinStr) : null;
        } catch (DateTimeParseException e) {
            // Gérer l'erreur de format de date si nécessaire
            System.err.println("Erreur de parsing des dates: " + e.getMessage());
        }

        String immat = immatriculation != null && immatriculation.isEmpty() ? null : immatriculation;

        return paiementRepository.findPaiementsByCriteria(dateDebut, dateFin, immat);
    }
}