package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Camions;
import com.example.demo.repository.CamionsRepository;

@Service
public class CamionsService {

    @Autowired
    private CamionsRepository camionRepository;

    public List<Camions> getAllCamions() {
        return camionRepository.findAll();
    }
}