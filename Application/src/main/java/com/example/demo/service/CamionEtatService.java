package com.example.demo.service;

import com.example.demo.view.VCamionEtat;
import com.example.demo.repository.VCamionEtatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamionEtatService {

    @Autowired
    private VCamionEtatRepository camionEtatRepository;

    public List<VCamionEtat> getAllCamionsAvecEtat() {
        return camionEtatRepository.findAll();
    }

    public List<VCamionEtat> getAllCamionsDisponibles() {
        return camionEtatRepository.findByEtatCamion("Disponnible");
    }
}
