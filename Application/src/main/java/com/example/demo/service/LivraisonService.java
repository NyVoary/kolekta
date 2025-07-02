package com.example.demo.service;

import com.example.demo.dto.LivraisonDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.LivraisonRepository;
import com.example.demo.repository.CommandeCamionRepository;    
import com.example.demo.repository.CommandeStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LivraisonService {

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private CommandeCamionRepository commandeCamionRepository;

    @Autowired
    private CommandeStatusRepository commandeStatusRepository;

    public List<LivraisonDTO> getAllLivraisons() {
        List<Livraison> livraisons = livraisonRepository.findAll();
        List<LivraisonDTO> livraisonDTOs = new ArrayList<>();

        for (Livraison livraison : livraisons) {
            Commande commande = livraison.getCommande();
            Client client = commande.getClient();

            // Recherche du camion associé
            CommandeCamion commandeCamion = commandeCamionRepository.findByCommandeIdCommande(commande.getIdCommande())
                    .orElse(null);
            Camion camion = (commandeCamion != null) ? commandeCamion.getAssignationCamion().getCamion() : null;

            // Recherche du statut actuel (le plus récent)
            String statut = "Non défini";
            List<CommandeStatus> commandeStatuses = commandeStatusRepository.findByCommandeIdCommande(commande.getIdCommande());
            if (!commandeStatuses.isEmpty()) {
                CommandeStatus dernierStatus = commandeStatuses.stream()
                        .max(Comparator.comparing(CommandeStatus::getDateChangement))
                        .orElse(null);
                if (dernierStatus != null && dernierStatus.getStatus() != null) {
                    statut = dernierStatus.getStatus().getLibelle();
                }
            }

            LivraisonDTO dto = new LivraisonDTO();
            dto.setIdLivraison(livraison.getIdLivraison());
            dto.setDateLivraison(livraison.getDateLivraison());
            dto.setClientNom(client.getNom() + " " + client.getPrenoms());
            dto.setAdresseLivraison(commande.getAdresseLivraison());
            dto.setVilleLivraison(commande.getVilleLivraison());
            dto.setImmatriculation(camion != null ? camion.getImmatriculation() : "Non assigné");
            dto.setChauffeurNom(camion != null ? camion.getChauffeurNom() : "Non assigné");
            dto.setStatut(statut);

            livraisonDTOs.add(dto);
        }
        return livraisonDTOs;
    }
}