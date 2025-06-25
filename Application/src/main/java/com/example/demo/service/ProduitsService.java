package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Produits;
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
        int mois = 2;
        return repository.listProduitsSaisonniers(mois);
    }
}
