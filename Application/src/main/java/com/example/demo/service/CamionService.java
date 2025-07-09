package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CamionDTO;
import com.example.demo.entity.Camions;
import com.example.demo.repository.CamionsRepository;
// import com.example.demo.entity.Camion;
// import com.example.demo.repository.CamionRepository;

@Service
public class CamionService { 

    @Autowired
    private CamionsRepository camionRepository;

    public void ajouterCamion(CamionDTO camionDTO) throws Exception{
        Camions camion = new Camions();
        camion.setCapacite(camionDTO.getCapacite());
        camion.setImmatriculation(camionDTO.getImmatriculation());
        camion.setChauffeurNom(camionDTO.getChauffeur_nom());
        camion.setChauffeurTelephone(camionDTO.getChauffeur_telephone());
        camionRepository.save(camion);
    }

     public List<Camions> getAll() throws Exception {
        try {
            return camionRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des camions : " + e.getMessage());
        }
    }
}
