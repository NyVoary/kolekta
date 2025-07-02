package com.example.demo.service;

import com.example.demo.repository.*;
import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommandeStatusRepository commandeStatusRepository;

    public List<CommandeDTO> getAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        List<CommandeDTO> commandeDTOs = new ArrayList<>();

        for (Commande commande : commandes) {
            Client client = commande.getClient();

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

            CommandeDTO dto = new CommandeDTO();
            dto.setIdCommande(commande.getIdCommande());
            dto.setDateCommande(commande.getDateCommande());
            dto.setFraisLivraison(commande.getFraisLivraison());
            dto.setMontantTotal(commande.getMontantTotal());
            dto.setAdresseLivraison(commande.getAdresseLivraison());
            dto.setVilleLivraison(commande.getVilleLivraison());
            dto.setTelephoneLivraison(commande.getTelephoneLivraison());
            dto.setEmailLivraison(commande != null ? commande.getEmailLivraison() : "Non défini");
            dto.setClientNom(client.getNom() + " " + client.getPrenoms());
            dto.setStatut(statut);

            commandeDTOs.add(dto);
        }
        return commandeDTOs;
    }
}