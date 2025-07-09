package com.example.demo.service;

import com.example.demo.entity.AssignationsTaches;
import com.example.demo.entity.StatusTaches;
import com.example.demo.repository.AssignationsTachesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<AssignationsTaches> findById(Long id) {
        return assignationsTachesRepository.findById(id);
    }

    public AssignationsTaches save(AssignationsTaches tache) {
        return assignationsTachesRepository.save(tache);
    }

    public void changerStatutTache(Long tacheId, StatusTaches nouveauStatut) {
        assignationsTachesRepository.findById(tacheId).ifPresent(tache -> {
            tache.setStatus(nouveauStatut);
            assignationsTachesRepository.save(tache);
        });
    }
}