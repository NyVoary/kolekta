package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.*;
import com.example.demo.entity.Livraison;
import com.example.demo.repository.LivraisonRepository;
import com.example.demo.repository.CommandeRepository;

@Service
public class LivraisonService {

    private final LivraisonRepository livraisonRepository;
    private final CommandeRepository commandeRepository;

    public LivraisonService(LivraisonRepository livraisonRepository, CommandeRepository commandeRepository) {
        this.livraisonRepository = livraisonRepository;
        this.commandeRepository = commandeRepository;
    }

    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }

    public Livraison saveLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    public Commandes getCommande(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public Livraison getById(Long id) {
        return livraisonRepository.findById(id).orElse(null);
    }

    public void update(Long id, Livraison livraison, Commandes commandes) {
        Commandes commande = commandeRepository.findById(id).orElse(null);

        if (commande != null) {
            // Update the existing commande
            commande.setAdresseLivraison(commandes.getAdresseLivraison());
            commande.setVilleLivraison(commandes.getVilleLivraison());
            commande.setTelephoneLivraison(commandes.getTelephoneLivraison());
            commande.setEmailLivraison(commandes.getEmailLivraison());
            commandeRepository.save(commande);

            // Create new livraison and set BOTH required fields
            Livraison nouvelleLivraison = new Livraison();
            nouvelleLivraison.setCommande(commande); // THIS WAS MISSING!
            nouvelleLivraison.setDateLivraison(livraison.getDateLivraison());
            livraisonRepository.save(nouvelleLivraison);
        }
    }
}