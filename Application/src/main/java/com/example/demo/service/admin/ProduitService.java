package com.example.demo.service.admin;

import com.example.demo.entity.admin.Produit;
import com.example.demo.repository.admin.ProduitRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {
    private final ProduitRepository repository;

    public ProduitService(ProduitRepository repository) {
        this.repository = repository;
    }

    public List<Produit> getAll() {
        return repository.findAll();
    }
    public Produit getById(int id) {
        return repository.findById(id).orElse(null);
    }
    public Produit save(Produit produit) {
        return repository.save(produit);
    }
    public void deleteById(int id) {
        repository.deleteById(id);
    }
    public List<Produit> getByCategorieId(int id) {
        return repository.findAll().stream()
                .filter(produit -> produit.getId_categorie() == id)
                .toList();
    }
    public List<Produit> getByFournisseurId(int id) {
        return repository.findAll().stream()
                .filter(produit -> produit.getId_fournisseur() == id)
                .toList();
    }
    // etc 
}