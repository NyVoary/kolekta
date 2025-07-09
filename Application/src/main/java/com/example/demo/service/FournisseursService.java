package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Fournisseur;
import com.example.demo.repository.FournisseursRepository;

@Service
public class FournisseursService {
    @Autowired
    private FournisseursRepository fournisseurRepository;

    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }
}