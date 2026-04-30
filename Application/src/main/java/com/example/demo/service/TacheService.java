package com.example.demo.service;

import com.example.demo.entity.AssignationTache;
import com.example.demo.entity.Employe;
import com.example.demo.repository.AssignationTacheRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TacheService {

    private final AssignationTacheRepository tacheRepository;

    public TacheService(AssignationTacheRepository tacheRepository) {
        this.tacheRepository = tacheRepository;
    }

    public List<AssignationTache> getTachesParEmploye(Employe employe) {
        if (employe == null || employe.getId() == null) {
            return Collections.emptyList();
        }
        return tacheRepository.findByEmploye(employe);
    }
}