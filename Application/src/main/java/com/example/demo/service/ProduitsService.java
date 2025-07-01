package com.example.demo.service;

import java.time.LocalDate;
// import java.time.LocalDate;
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

    public void delete(int id) {
        repository.deleteById(id);
    }

    public void saveOrUpdate(Produits produits) {
        repository.save(produits);
    }

    public List<Produits> getAll() {
        return repository.findAll();
    }

    public Produits getBYid(int id) {
        return repository.findById(id).get();
    }

    public List<Produits> listsProduitsSaisonniers() {
        int mois = LocalDate.now().getMonthValue();
        return repository.listProduitsSaisonniers(mois);
    }

    public List<Produits> rechercherProduits(String search, String categorie, Double prixMin, Double prixMax) {
        // Convertir les chaînes vides en null
        String searchParam = (search != null && search.isEmpty()) ? null : search;
        String categorieParam = (categorie != null && categorie.isEmpty()) ? null : categorie;

        return repository.findProduitsByCritere(searchParam, categorieParam, prixMin, prixMax);
    }
}
