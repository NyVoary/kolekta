package com.lokaly.service;

import com.lokaly.dto.camion.AssignationCamionRequest;
import com.lokaly.dto.camion.CamionRequest;
import com.lokaly.dto.camion.CamionResponse;

import java.util.List;

public interface CamionService {
    CamionResponse creer(CamionRequest request);
    CamionResponse modifier(Long id, CamionRequest request);
    CamionResponse trouverParId(Long id);
    List<CamionResponse> listerTous();
    void assigner(AssignationCamionRequest request);
}
