package com.example.demo.service;

import com.example.demo.entity.Disponibilite;
import com.example.demo.repository.DisponibiliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisponibiliteService {
    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    public Disponibilite save(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }

    public List<Disponibilite> findAll() {
        return disponibiliteRepository.findAll();
    }
}
