package com.example.demo.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.dto.StatistiqueDTO;
import com.example.demo.entity.AssignationTache;
import com.example.demo.repository.AssignationTacheRepository;

@Service
public class AssignationTacheService {
    
    private final AssignationTacheRepository repository;

    public AssignationTacheService(AssignationTacheRepository repository) {
        this.repository = repository;
    }

    public void add(AssignationTache tache) {
        repository.save(tache);
    }

    public AssignationTache getById(long id) {
        return repository.findById(id).get();
    }

    public StatistiqueDTO getStat() {
        Map<String, Object> raw = repository.getStatique();
        return new StatistiqueDTO(
            ((Number) raw.get("en_attente")).intValue(),
            ((Number) raw.get("en_cours")).intValue(),
            ((Number) raw.get("termine")).intValue(),
            ((Number) raw.get("total")).intValue()
        );
    }
}
