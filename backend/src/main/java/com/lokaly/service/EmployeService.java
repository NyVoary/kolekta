package com.lokaly.service;

import com.lokaly.dto.employe.AccesRequest;
import com.lokaly.dto.employe.EmployeRequest;
import com.lokaly.dto.employe.EmployeResponse;

import java.util.List;

public interface EmployeService {
    EmployeResponse creer(EmployeRequest request);
    EmployeResponse modifier(Long id, EmployeRequest request);
    EmployeResponse trouverParId(Long id);
    List<EmployeResponse> listerActifs();
    List<EmployeResponse> listerTous();
    void desactiver(Long id);
    void creerAcces(AccesRequest request);
}
