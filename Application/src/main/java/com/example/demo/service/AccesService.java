package com.example.demo.service;

import com.example.demo.entity.Acces;
import com.example.demo.repository.AccesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccesService {

    @Autowired
    private AccesRepository accesRepository;

    public String verifierIdentifiants(String username, String password) {
        return accesRepository.findByNomUtilisateur(username)
                .filter(acces -> acces.getMotDePasse().equals(password))
                .map(Acces::getRole)
                .orElse(null); // ou tu peux retourner "invalide" si tu préfères
    }

    public Acces getAccesByUsername(String username) {
        return accesRepository.findByNomUtilisateur(username)
                .orElse(null); // ou tu peux lancer une exception si l'utilisateur n'existe pas
    }
}
