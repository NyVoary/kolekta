package com.example.demo.service;

import com.example.demo.entity.StatusTaches;
import com.example.demo.repository.StatusTachesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusTachesService {

    private final StatusTachesRepository statusTachesRepository;

    @Autowired
    public StatusTachesService(StatusTachesRepository statusTachesRepository) {
        this.statusTachesRepository = statusTachesRepository;
    }

    /**
     * Récupère tous les statuts de tâches disponibles
     * @return Liste des StatusTaches
     */
    public List<StatusTaches> getAllStatusTaches() {
        return statusTachesRepository.findAll();
    }

    /**
     * Récupère un statut par son ID
     * @param id ID du statut
     * @return StatusTaches correspondant
     */
    public StatusTaches getStatusById(Long id) {
        return statusTachesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statut non trouvé avec l'ID: " + id));
    }
}