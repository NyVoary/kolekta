package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Client;
import com.example.demo.entity.Commande;
import com.example.demo.entity.CommandeProduit;
import com.example.demo.entity.Panier;
import com.example.demo.entity.PanierProduit;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.repository.CommandeProduitRepository;

@Service
public class CommandeService {

    @Autowired private CommandeRepository commandeRepository;
    @Autowired private CommandeProduitRepository commandeProduitRepository;
    @Autowired private PanierService panierService;
    @Autowired private ClientService clientService;

    public Commande creerCommandeDepuisPanier(Long idClient) {
        Panier panier = panierService.getPanierAvecProduits(idClient).orElseThrow();
        Client client = clientService.getClientById(idClient).orElseThrow();

        BigDecimal montantHT = panierService.calculerMontantHT(panier);
        BigDecimal montantTTC = panierService.calculerMontantTTC(montantHT);
        BigDecimal fraisLivraison = panierService.getFraisLivraison();
        BigDecimal montantTotal = panierService.calculerMontantTotal(montantTTC);

        Commande commande = new Commande();
        commande.setIdClient((Long)client.getIdClient());
        commande.setDateCommande(LocalDate.now());
        commande.setFraisLivraison(fraisLivraison);
        commande.setMontantTotal(montantTotal);
        commande.setAdresseLivraison(client.getAdresse());
        commande.setVilleLivraison("Antananarivo");
        commande.setTelephoneLivraison(client.getTelephone());
        commande.setEmailLivraison(client.getEmail());

        commande = commandeRepository.save(commande);

        for (PanierProduit pp : panier.getPanierProduits()) {
            CommandeProduit cp = new CommandeProduit();
            cp.setCommande(commande);
            cp.setProduit(pp.getProduit());
            cp.setQuantite(pp.getQuantite());
            cp.setPrixUnitaire(pp.getPrixUnitaire());
            commandeProduitRepository.save(cp);
        }

        panierService.viderPanier(idClient);
        return commande;
    }
}
