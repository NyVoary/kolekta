package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CamionDTO;
import com.example.demo.entity.Camion;
import com.example.demo.repository.CamionRepository;
// import com.example.demo.entity.Camion;
// import com.example.demo.repository.CamionRepository;

@Service
public class CamionService { 

    @Autowired
    private CamionRepository camionRepository;

    public void ajouterCamion(CamionDTO camionDTO) throws Exception{
        Camion camion = new Camion();
        camion.setCapacite(camionDTO.getCapacite());
        camion.setImmatriculation(camionDTO.getImmatriculation());
        camion.setChauffeur_nom(camionDTO.getChauffeur_nom());
        camion.setChauffeur_telephone(camionDTO.getChauffeur_telephone());
        camionRepository.save(camion);
    }

     public List<Camion> getAll() throws Exception {
        try {
            return camionRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des camions : " + e.getMessage());
        }
    }
}
