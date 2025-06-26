package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Commande;
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

    public Commande getCommande(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public Livraison getById(Long id) {
        return livraisonRepository.findById(id).orElse(null);
    }

    public void update(Long id, Livraison livraison, Commande commandes) {
        Commande commande = commandeRepository.findById(id).orElse(null);

        if (commande != null) {
            commande.setAdresseLivraison(commandes.getAdresseLivraison());
            commande.setVilleLivraison(commandes.getVilleLivraison());
            commande.setTelephoneLivraison(commandes.getTelephoneLivraison());
            commande.setEmailLivraison(commandes.getEmailLivraison());
            commandeRepository.save(commande);

            Livraison nouvelleLivraison = new Livraison();
            nouvelleLivraison.setCommande(commande);
            nouvelleLivraison.setDateLivraison(livraison.getDateLivraison());
            livraisonRepository.save(nouvelleLivraison);
        }
    }
}
