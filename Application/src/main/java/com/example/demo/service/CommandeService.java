package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommandeStatusRepository commandeStatusRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StatusRepository statusRepository;

    public List<CommandeDTO> getAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        List<CommandeDTO> commandeDTOs = new ArrayList<>();

        for (Commande commande : commandes) {
            Client client = commande.getClient();

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
            dto.setEmailLivraison(commande.getEmailLivraison());
            dto.setClientNom(client.getNom() + " " + client.getPrenoms());
            dto.setStatut(statut);

            commandeDTOs.add(dto);
        }
        return commandeDTOs;
    }

    public Commande ajouterCommande(Long idClient, BigDecimal fraisLivraison, String adresseLivraison,
                                   String villeLivraison, String telephoneLivraison, String emailLivraison,
                                   List<Long> produitIds, List<Integer> quantites) {
        // Créer la commande
        Commande commande = new Commande();
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        commande.setClient(client);
        commande.setDateCommande(LocalDate.now());
        commande.setFraisLivraison(fraisLivraison);
        commande.setAdresseLivraison(adresseLivraison);
        commande.setVilleLivraison(villeLivraison);
        commande.setTelephoneLivraison(telephoneLivraison);
        commande.setEmailLivraison(emailLivraison);

        // Calculer le montant total
        BigDecimal montantTotal = BigDecimal.ZERO;
        List<CommandeProduit> commandeProduits = new ArrayList<>();
        for (int i = 0; i < produitIds.size(); i++) {
            final Long produitId = produitIds.get(i); // Capture produitId as final
            Produit produit = produitRepository.findById(produitId)
                    .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé: " + produitId));
            Integer quantite = quantites.get(i);
            CommandeProduit commandeProduit = new CommandeProduit();
            commandeProduit.setCommande(commande);
            commandeProduit.setProduit(produit);
            commandeProduit.setQuantite(quantite);
            commandeProduit.setPrixUnitaire(produit.getPrixVente());
            commandeProduits.add(commandeProduit);
            montantTotal = montantTotal.add(produit.getPrixVente().multiply(BigDecimal.valueOf(quantite)));
        }
        montantTotal = montantTotal.add(fraisLivraison);
        commande.setMontantTotal(montantTotal);
        commande.setCommandeProduits(commandeProduits);

        // Enregistrer la commande
        commande = commandeRepository.save(commande);

        // Ajouter un statut initial
        CommandeStatus commandeStatus = new CommandeStatus();
        commandeStatus.setCommande(commande);
        commandeStatus.setStatus(statusRepository.findByLibelle("En attente de validation")
                .orElseThrow(() -> new IllegalArgumentException("Statut 'En attente de validation' non trouvé")));
        commandeStatus.setDateChangement(LocalDate.now());
        commandeStatus.setClient(client);
        commandeStatusRepository.save(commandeStatus);

        return commande;
    }
}