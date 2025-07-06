package com.example.demo.service;

import org.springframework.stereotype.Service;

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
}
