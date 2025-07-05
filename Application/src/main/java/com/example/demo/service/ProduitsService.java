package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Produits;
import com.example.demo.repository.ProduitsRepository;

@Service
public class ProduitsService {

    private final ProduitsRepository repository;

    @Autowired
    public ProduitsService(ProduitsRepository produitsRepository) {
        this.repository = produitsRepository;
    }

    public void delete(Long id) { // Changé de int à Long
        repository.deleteById(id);
    }

    public void saveOrUpdate(Produits produits) {
        repository.save(produits);
    }

    public List<Produits> getAll() {
        return repository.findAll();
    }

    public Produits getBYid(Long id) { // Changé de int à Long
        return repository.findById(id).orElse(null);
    }

    public List<Produits> listsProduitsSaisonniers() {
        int mois = LocalDate.now().getMonthValue();
        return repository.listProduitsSaisonniers(mois);
    }

    public List<Produits> rechercherProduits(String search, String categorie, Double prixMin, Double prixMax) {
        // Convertir les chaînes vides en null
        String searchParam = (search != null && search.trim().isEmpty()) ? null : search;
        String categorieParam = (categorie != null && categorie.trim().isEmpty()) ? null : categorie;

        try {
            // Essayer d'abord avec la requête JPQL
            return repository.findProduitsByCritere(searchParam, categorieParam, prixMin, prixMax);
        } catch (Exception e) {
            // Si la requête JPQL échoue, utiliser la requête native
            System.err.println("Erreur avec la requête JPQL, utilisation de la requête native: " + e.getMessage());
            return repository.findProduitsByCritereNative(searchParam, categorieParam, prixMin, prixMax);
        }
    }
}