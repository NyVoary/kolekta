package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AssignationsTaches;
import com.example.demo.entity.Employe;
import com.example.demo.entity.HistoriqueStatusTaches;
import com.example.demo.entity.StatusTaches;
import com.example.demo.repository.AssignationsTachesRepository;
import com.example.demo.repository.EmployeRepository;
import com.example.demo.repository.HistoriqueStatusTachesRepository;
import com.example.demo.repository.StatusTachesRepository;

import java.time.LocalDateTime;

@Service
public class HistoriqueStatusService {

    @Autowired
    private HistoriqueStatusTachesRepository historiqueRepository;
    
    @Autowired
    private AssignationsTachesRepository tacheRepository;
    
    @Autowired
    private EmployeRepository employeRepository;
    
    @Autowired
    private StatusTachesRepository statusRepository;

    public void enregistrerHistorique(Long tacheId, Long employeId, Long statusId) {
        // Récupérer les entités associées
        AssignationsTaches tache = tacheRepository.findById(tacheId)
            .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
        
        Employe employe = employeRepository.findById(employeId)
            .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        
        StatusTaches status = statusRepository.findById(statusId)
            .orElseThrow(() -> new RuntimeException("Statut non trouvé"));

        // Créer et enregistrer le nouvel historique
        HistoriqueStatusTaches historique = new HistoriqueStatusTaches();
        historique.setTache(tache);
        historique.setModifiePar(employe);
        historique.setStatusTache(status);
        historique.setDate(LocalDateTime.now());
        
        historiqueRepository.save(historique);
    }
}