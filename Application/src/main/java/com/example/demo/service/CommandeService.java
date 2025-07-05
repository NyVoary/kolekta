package com.example.demo.service;

<<<<<<< HEAD
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


@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired private CommandeProduitRepository commandeProduitRepository;
    @Autowired private PanierService panierService;
    @Autowired private ClientService clientService;


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
}
=======
import com.example.demo.entity.Commande;
import com.example.demo.entity.Client;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.repository.CommandeStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommandeService {
    private CommandeRepository commandeRepository;
    private CommandeStatusRepository commandeStatusRepository;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository, 
                         CommandeStatusRepository commandeStatusRepository) {
        this.commandeRepository = commandeRepository;
        this.commandeStatusRepository = commandeStatusRepository;
    }
    public List<Commande> getCommandesByClient(Client client) {
        return commandeRepository.findByClientOrderByDateCommandeDesc(client);
    }

    public List<Commande> getCommandesWithProduitsByClientId(int clientId) {
        return commandeRepository.findWithProduitsByClientId(clientId);
    }

    public List<Commande> getAllCommandesByClientWithProduits(int clientId) {
        return commandeRepository.findAllByClientIdWithProduits(clientId);
    }

    public String getDernierStatutCommande(Long commandeId) {
        return commandeStatusRepository.findTopByCommandeIdOrderByDateChangementDesc(commandeId)
                .map(cs -> cs.getStatus().getLibelle())
                .orElse("Inconnu");
    }
}
>>>>>>> origin/sarobidy-admin
