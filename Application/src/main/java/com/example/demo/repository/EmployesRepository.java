package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employes;

public interface EmployesRepository extends JpaRepository<Employes, Long>{
    
}
