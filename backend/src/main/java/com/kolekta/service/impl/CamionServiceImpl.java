package com.kolekta.service.impl;

import com.kolekta.dto.camion.AssignationCamionRequest;
import com.kolekta.dto.camion.CamionRequest;
import com.kolekta.dto.camion.CamionResponse;
import com.kolekta.entity.*;
import com.kolekta.exception.BadRequestException;
import com.kolekta.exception.ResourceNotFoundException;
import com.kolekta.repository.*;
import com.kolekta.service.CamionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CamionServiceImpl implements CamionService {

    private final CamionsRepository camionsRepository;
    private final StatusRepository statusRepository;
    private final EmployesRepository employesRepository;
    private final TypeAssignationRepository typeAssignationRepository;
    private final AssignationsCamionsRepository assignationsCamionsRepository;

    @Override
    @Transactional
    public CamionResponse creer(CamionRequest request) {
        if (camionsRepository.existsByImmatriculation(request.getImmatriculation())) {
            throw new BadRequestException("Immatriculation déjà utilisée");
        }
        Status status = statusRepository.findById(request.getIdStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Status", request.getIdStatus()));

        Camions c = new Camions();
        c.setImmatriculation(request.getImmatriculation());
        c.setModele(request.getModele());
        c.setCapaciteKg(request.getCapaciteKg());
        c.setStatus(status);

        return toResponse(camionsRepository.save(c));
    }

    @Override
    @Transactional
    public CamionResponse modifier(Long id, CamionRequest request) {
        Camions c = camionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Camion", id));
        Status status = statusRepository.findById(request.getIdStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Status", request.getIdStatus()));

        c.setImmatriculation(request.getImmatriculation());
        c.setModele(request.getModele());
        c.setCapaciteKg(request.getCapaciteKg());
        c.setStatus(status);

        return toResponse(camionsRepository.save(c));
    }

    @Override
    public CamionResponse trouverParId(Long id) {
        return toResponse(camionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Camion", id)));
    }

    @Override
    public List<CamionResponse> listerTous() {
        return camionsRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void assigner(AssignationCamionRequest request) {
        Camions camion = camionsRepository.findById(request.getIdCamion())
                .orElseThrow(() -> new ResourceNotFoundException("Camion", request.getIdCamion()));
        Employes employe = employesRepository.findById(request.getIdEmploye())
                .orElseThrow(() -> new ResourceNotFoundException("Employe", request.getIdEmploye()));
        TypeAssignation type = typeAssignationRepository.findById(request.getIdTypeAssignation())
                .orElseThrow(() -> new ResourceNotFoundException("TypeAssignation", request.getIdTypeAssignation()));

        AssignationsCamions a = new AssignationsCamions();
        a.setCamion(camion);
        a.setEmploye(employe);
        a.setDateAssignation(request.getDateAssignation());
        a.setTypeAssignation(type);
        assignationsCamionsRepository.save(a);
    }

    private CamionResponse toResponse(Camions c) {
        return CamionResponse.builder()
                .idCamion(c.getIdCamion())
                .immatriculation(c.getImmatriculation())
                .modele(c.getModele())
                .capaciteKg(c.getCapaciteKg())
                .status(c.getStatus().getLibelle())
                .build();
    }
}
