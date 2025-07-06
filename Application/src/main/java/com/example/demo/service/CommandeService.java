package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dto.request.FilterCommandesRequest;
import com.example.demo.dto.response.CommandeClientResponse;
import java.util.List;

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
    @Autowired private CommandeProduitRepository commandeProduitRepository;
    @Autowired private PanierService panierService;
    @Autowired private ClientService clientService;
    @Autowired private CommandeStatusRepository commandeStatusRepository;
    @Autowired
    private ProduitsRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StatusRepository statusRepository;



    public Commandes creerCommandeDepuisPanier(Long idClient) {
        Panier panier = panierService.getPanierAvecProduits(idClient).orElseThrow();
        Client client = clientService.getClientById(idClient.longValue()).orElseThrow();

        Double montantHT = panierService.calculerMontantHT(panier);
        Double montantTTC = panierService.calculerMontantTTC(montantHT);
        Double fraisLivraison = panierService.getFraisLivraison(montantHT);
        Double montantTotal = panierService.calculerMontantTotal(montantTTC);

        Commandes commande = new Commandes();
        commande.setClient(client);
        commande.setDateCommande(LocalDate.now());
        commande.setFraisLivraison(fraisLivraison.doubleValue());
        commande.setMontantTotal(montantTotal.doubleValue());
        commande.setAdresseLivraison(client.getAdresse());
        commande.setVilleLivraison("Antananarivo");
        commande.setTelephoneLivraison(client.getTelephone());
        commande.setEmailLivraison(client.getEmail());

        commande = commandeRepository.save(commande);

        for (PanierProduits pp : panier.getPanierProduits()) {
            CommandeProduit cp = new CommandeProduit();
            cp.setCommande(commande);
            cp.setProduit(pp.getProduit());
            cp.setQuantite(pp.getQuantite() != null ? pp.getQuantite().intValue() : null);
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
            commande.getProduits().size();
        }
        return commande;
    }

    public List<Commandes> getCommandesByClientId(Long client) {
        return commandeRepository.findByClientId(client);
    }

    // ok 
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
