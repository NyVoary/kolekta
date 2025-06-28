package com.example.demo.service;

import com.example.demo.entity.Produits;
import com.example.demo.repository.ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProduitsService {
    
    @Autowired
    private ProduitsRepository produitsRepository;

    public Produits save(Produits produit) {
        return produitsRepository.save(produit);
    }
    public Produits findById(int id) {
        return produitsRepository.findById(id).orElse(null);
    }
    public void deleteById(int id) {
        produitsRepository.deleteById(id);
    }
    public Iterable<Produits> findAll() {
        return produitsRepository.findAll();
    }
    
}
