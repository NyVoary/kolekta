package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.LivraisonDTO;
import com.example.demo.entity.Livraison;
import com.example.demo.entity.Commande;
import com.example.demo.entity.Client;
import com.example.demo.entity.Camion;
import com.example.demo.entity.CommandeCamion;
import com.example.demo.repository.LivraisonRepository;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.repository.CommandeCamionRepository;

@Service
public class LivraisonService {

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommandeCamionRepository commandeCamionRepository;

    public List<LivraisonDTO> getAllLivraisons() {
        List<Livraison> livraisons = livraisonRepository.findAll();
        List<LivraisonDTO> livraisonDTOs = new ArrayList<>();

        for (Livraison livraison : livraisons) {
            Commande commande = livraison.getCommande();
            Client client = commande.getClient();

            // Recherche du camion associé via CommandeCamion et AssignationCamion
            CommandeCamion commandeCamion = commandeCamionRepository.findByCommandeIdCommande(commande.getIdCommande()).orElse(null);
            Camion camion = (commandeCamion != null) ? commandeCamion.getAssignationCamion().getCamion() : null;

            LivraisonDTO dto = new LivraisonDTO();
            dto.setIdLivraison(livraison.getIdLivraison());
            dto.setDateLivraison(livraison.getDateLivraison());
            dto.setClientNom(client.getNom() + " " + client.getPrenoms());
            dto.setAdresseLivraison(commande.getAdresseLivraison());
            dto.setVilleLivraison(commande.getVilleLivraison());
            dto.setImmatriculation(camion != null ? camion.getImmatriculation() : "Non assigné");
            dto.setChauffeurNom(camion != null ? camion.getChauffeurNom() : "Non assigné");

            livraisonDTOs.add(dto);
        }
        return livraisonDTOs;
    }
}