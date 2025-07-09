package com.example.demo.service;

import com.example.demo.dto.CommandeDTO;
import com.example.demo.dto.request.FilterCommandesRequest;
import com.example.demo.dto.response.CommandeClientResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeProduitsRepository commandeProduitRepository;
    @Autowired
    private PanierService panierService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CommandeStatusRepository commandeStatusRepository;
    @Autowired
    private ProduitsRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired 
    private CommandeProduitsRepository commandeProduitsRepository ;

    public Commandes getCommandeById(Long id) {
    return commandeRepository.findById(id).orElse(null);
}

    public Commandes creerCommandeDepuisPanier(Long idClient) {
        Panier panier = panierService.getPanierAvecProduits(idClient).orElseThrow();
        Client client = clientService.getClientById(idClient).orElseThrow();

        Double montantHT = panierService.calculerMontantHT(panier);
        Double montantTTC = panierService.calculerMontantTTC(montantHT);
        Double fraisLivraison = panierService.getFraisLivraison(montantHT);
        Double montantTotal = panierService.calculerMontantTotal(montantTTC);

        Commandes commande = new Commandes();
        commande.setClient(client);
        commande.setDateCommande(LocalDate.now());
        commande.setFraisLivraison(fraisLivraison);
        commande.setMontantTotal(montantTTC);
        commande.setAdresseLivraison(client.getAdresse());
        commande.setVilleLivraison("Antananarivo");
        commande.setTelephoneLivraison(client.getTelephone());
        commande.setEmailLivraison(client.getEmail());

        commande = commandeRepository.save(commande);

        for (PanierProduits pp : panier.getPanierProduits()) {
            CommandeProduits cp = new CommandeProduits();
            cp.setCommande(commande);
            cp.setProduit(pp.getProduit());
            cp.setQuantite(pp.getQuantite().intValue());
            // cp.setQuantite(pp.getQuantite().intValue() != null ? pp.getQuantite().intValue() : null);
            cp.setPrixUnitaire(pp.getPrixUnitaire());
            commandeProduitRepository.save(cp);
        }

        panierService.viderPanier(idClient);
        return commande;
    }

    @Transactional(readOnly = true)
    public Commandes getCommandeWithProduits(Long id) {
        Commandes commande = commandeRepository.findById(id).orElse(null);
        if (commande != null) {
            commande.getCommandeProduits().size();
        }
        return commande;
    }

    public List<Commandes> getCommandesByClientId(Long client) {
        return commandeRepository.findByClientId(client);
    }

    public List<Commandes> getCommandesByClient(Client client) {
        return commandeRepository.findByClientOrderByDateCommandeDesc(client);
    }

    public List<Commandes> getCommandesWithProduitsByClientId(int clientId) {
        return commandeRepository.findWithProduitsByClientId(clientId);
    }

    public List<Commandes> getAllCommandesByClientWithProduits(int clientId) {
        return commandeRepository.findAllByClientIdWithProduits(clientId);
    }

    public String getDernierStatutCommande(Long commandeId) {
        return commandeStatusRepository.findTopByCommandeIdOrderByDateChangementDesc(commandeId)
                .map(cs -> cs.getStatus().getLibelle())
                .orElse("Inconnu");
    }

    public List<CommandeDTO> getAllCommandes() {
        List<Commandes> commandes = commandeRepository.findAll();
        List<CommandeDTO> commandeDTOs = new ArrayList<>();

        for (Commandes commande : commandes) {
            Client client = commande.getClient();

            String statut = "Non défini";
            List<CommandeStatus> commandeStatuses = commandeStatusRepository.findByCommandeId(commande.getId());
            if (!commandeStatuses.isEmpty()) {
                CommandeStatus dernierStatus = commandeStatuses.stream()
                        .max(Comparator.comparing(CommandeStatus::getId))
                        .orElse(null);
                if (dernierStatus != null && dernierStatus.getStatus() != null) {
                    statut = dernierStatus.getStatus().getLibelle();
                }
            }

            CommandeDTO dto = new CommandeDTO();
            dto.setIdCommande(commande.getId());
            dto.setDateCommande(commande.getDateCommande());
            dto.setFraisLivraison(commande.getFraisLivraison());
            dto.setMontantTotal(commande.getMontantTotal());
            dto.setAdresseLivraison(commande.getAdresseLivraison());
            dto.setVilleLivraison(commande.getVilleLivraison());
            dto.setTelephoneLivraison(commande.getTelephoneLivraison());
            dto.setEmailLivraison(commande.getEmailLivraison());
            dto.setClientNom(client.getNom() + " " + client.getPrenoms());
            dto.setStatut(statut);
            dto.setCommandeProduits(commandeProduitsRepository.findByCommandeId(dto.getIdCommande()));
            commandeDTOs.add(dto);
        }
        return commandeDTOs;
    }

    @Transactional
    public Commandes ajouterCommande(Long idClient, Double fraisLivraison, String adresseLivraison,
                                   String villeLivraison, String telephoneLivraison, String emailLivraison,
                                   List<Long> produitIds, List<Integer> quantites) {
        Commandes commande = new Commandes();
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        commande.setClient(client);
        commande.setDateCommande(LocalDate.now());
        commande.setFraisLivraison(fraisLivraison);
        commande.setAdresseLivraison(adresseLivraison);
        commande.setVilleLivraison(villeLivraison);
        commande.setTelephoneLivraison(telephoneLivraison);
        commande.setEmailLivraison(emailLivraison);

        Double montantTotal = 0.0;
        List<CommandeProduits> commandeProduits = new ArrayList<>();
        for (int i = 0; i < produitIds.size(); i++) {
            Long produitId = produitIds.get(i);
            Produits produit = produitRepository.findById(produitId)
                    .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé: " + produitId));
            Integer quantite = quantites.get(i);
            CommandeProduits commandeProduit = new CommandeProduits();
            commandeProduit.setCommande(commande);
            commandeProduit.setProduit(produit);
            commandeProduit.setQuantite(quantite);
            commandeProduit.setPrixUnitaire(produit.getPrixVente());
            commandeProduits.add(commandeProduit);
            montantTotal += produit.getPrixVente() * quantite;
        }
        montantTotal += fraisLivraison;
        commande.setMontantTotal(montantTotal);
        commande.setCommandeProduits(commandeProduits);

        commande = commandeRepository.save(commande);

        CommandeStatus commandeStatus = new CommandeStatus();
        commandeStatus.setCommande(commande);
        commandeStatus.setStatus(statusRepository.findByLibelle("En attente de validation"));
        commandeStatus.setDateChangement(LocalDate.now());
        commandeStatus.setClient(client);
        commandeStatusRepository.save(commandeStatus);

        return commande;
    }
}
