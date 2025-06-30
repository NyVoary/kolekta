package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Produit;
import com.example.demo.repository.ProduitRepository;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> getTousLesProduits() {
        return produitRepository.findAll();
    }

    public List<Produit> rechercherProduits(String search, String categorie, Double prixMin, Double prixMax) {
        // Convertir les chaînes vides en null
        String searchParam = (search != null && search.isEmpty()) ? null : search;
        String categorieParam = (categorie != null && categorie.isEmpty()) ? null : categorie;
    
    return produitRepository.findProduitsByCritere(searchParam, categorieParam, prixMin, prixMax);
    }
}