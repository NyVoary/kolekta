package com.example.demo.service;

import com.example.demo.entity.AssignationsTaches;
import com.example.demo.repository.AssignationsTachesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AssignationTacheService {

    private final AssignationsTachesRepository assignationsTachesRepository;

    public AssignationTacheService(AssignationsTachesRepository assignationsTachesRepository) {
        this.assignationsTachesRepository = assignationsTachesRepository;
    }

    public List<AssignationsTaches> getTachesAujourdhuiParEmploye(Long idEmploye) {
        LocalDate aujourdhui = LocalDate.now();
        return assignationsTachesRepository.findTachesByEmployeAndDateOrderByPrioriteAndStatus(idEmploye, aujourdhui);
    }

    public List<AssignationsTaches> getTachesParEmployeEtDate(Long idEmploye, LocalDate date) {
        return assignationsTachesRepository.findTachesByEmployeAndDateOrderByPrioriteAndStatus(idEmploye, date);
    }
}