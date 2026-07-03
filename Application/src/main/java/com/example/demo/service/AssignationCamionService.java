package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AssignationCamionDTO;
import com.example.demo.entity.AssignationCamion;
import com.example.demo.entity.Camion;
import com.example.demo.repository.AssignationCamionRepository;
import com.example.demo.repository.CamionRepository;

@Service
public class AssignationCamionService {

    @Autowired
    private AssignationCamionRepository assignationRepo;

    @Autowired
    private CamionRepository camionRepo;

    public void ajouterAssignation(AssignationCamionDTO dto) throws Exception {
        Camion camion = camionRepo.findById(dto.getIdCamion())
            .orElseThrow(() -> new Exception("Camion introuvable avec l'id : " + dto.getIdCamion()));

        AssignationCamion ac = new AssignationCamion();
        ac.setCamion(camion);
        ac.setDepart(LocalDateTime.parse(dto.getDepart()));
        if (dto.getArrivee() != null && !dto.getArrivee().isEmpty()) {
            ac.setArrivee(LocalDateTime.parse(dto.getArrivee()));
        }
        ac.setType(AssignationCamion.TypeAssignation.valueOf(dto.getType()));

        assignationRepo.save(ac);
    }

    public AssignationCamion findById(int id) throws Exception{ 
        return assignationRepo.findById(id).get(); 
    }
}
