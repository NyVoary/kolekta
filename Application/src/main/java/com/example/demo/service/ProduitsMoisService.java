package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProduitsMois;
import com.example.demo.repository.ProduitsMoisRepository;

@Service
public class ProduitsMoisService {

    private final ProduitsMoisRepository repository;

    @Autowired
    public ProduitsMoisService(ProduitsMoisRepository produitsMoisRepository) {
        this.repository = produitsMoisRepository;
    }

    public void saveOrUpdate(ProduitsMois produitsMois) {
        repository.save(produitsMois);
    }

    public List<ProduitsMois> getAll() {
        return repository.findAll();
    }

    public ProduitsMois getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}