package com.kolekta.service;

import com.kolekta.dto.camion.AssignationCamionRequest;
import com.kolekta.dto.camion.CamionRequest;
import com.kolekta.dto.camion.CamionResponse;

import java.util.List;

public interface CamionService {
    CamionResponse creer(CamionRequest request);
    CamionResponse modifier(Long id, CamionRequest request);
    CamionResponse trouverParId(Long id);
    List<CamionResponse> listerTous();
    void assigner(AssignationCamionRequest request);
}
