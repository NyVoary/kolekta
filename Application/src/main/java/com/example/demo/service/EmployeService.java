package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employe;
import com.example.demo.repository.EmployeRepository;

@Service
public class EmployeService {
    
    private final EmployeRepository employeRepository;
    
    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }
    
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }
}