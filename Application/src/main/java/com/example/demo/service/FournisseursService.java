package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Fournisseurs;
import com.example.demo.repository.FornisseursRepository;

@Service
public class FournisseursService {

    private final FornisseursRepository repository;

    @Autowired
    public FournisseursService(FornisseursRepository fournisseursRepository) {
        this.repository = fournisseursRepository;
    }

    public List<Fournisseurs> getAll() {
        return repository.findAll();
    }

    public Fournisseurs getById(int id) {
        return repository.findById(id).orElse(null);
    }
}