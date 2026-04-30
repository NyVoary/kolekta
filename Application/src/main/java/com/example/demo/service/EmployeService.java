package com.example.demo.service;

import com.example.demo.entity.Employe;
import com.example.demo.repository.EmployeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmployeService {

    private final EmployeRepository employeRepository;

    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    public Optional<Employe> findById(Long id) {
        return employeRepository.findById(id);
    }
}