package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employes;
import com.example.demo.repository.EmployesRepository;

@Service
public class EmployesService {
    
    private final EmployesRepository repository;

    @Autowired
    public EmployesService(EmployesRepository repository) {
        this.repository = repository;
    }

    public void CreatOrUpdate(Employes employes) {
        repository.save(employes);
    }

    public Employes GetById(Long id) {
        return repository.findById(id).get();
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }
}
