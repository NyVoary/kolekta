package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Disponibilite;
import com.example.demo.entity.Fournisseurs;
import com.example.demo.entity.Produits;
import com.example.demo.repository.*;

@Service
public class FournisseursService {
    @Autowired
    private FornisseursRepository fournisseursRepository;
    @Autowired
    private ProduitsRepository produitsRepository;
    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    @Autowired
    public FournisseursService(FornisseursRepository fournisseursRepository) {
        this.fournisseursRepository = fournisseursRepository;
    }

    public List<Fournisseurs> getAll() {
        return fournisseursRepository.findAll();
    }

    public Fournisseurs getById(Long id) {
        return fournisseursRepository.findById(id).orElse(null);
    }

    public Fournisseurs save(Fournisseurs fournisseur) {
        return fournisseursRepository.save(fournisseur);
    }

    public List<Fournisseurs> findAll() {
        List<Fournisseurs> fournisseurs = fournisseursRepository.findAll();
        System.out.println("[DEBUG] Fournisseurs trouvés: " + fournisseurs.size());
        for (Fournisseurs f : fournisseurs) {
            System.out.println("[DEBUG] Fournisseur: id=" + f.getId() + ", nom=" + f.getNom());
        }
        return fournisseurs;
    }

    public Fournisseurs findById(Long id) {
        return fournisseursRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        fournisseursRepository.deleteById(id);
    }

    public List<Produits> getProduitsByFournisseur(Long fournisseurId) {
        List<Produits> produits = produitsRepository.findByFournisseur_Id(fournisseurId);
        System.out.println("[DEBUG] Produits pour fournisseur " + fournisseurId + " : " + produits.size());
        for (Produits p : produits) {
            System.out.println("[DEBUG] Produit: id=" + p.getId() + ", nom=" + p.getNom());
        }
        return produits;
    }

    public Disponibilite saveDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }

    public List<Map<String, Object>> getProduitsAvecQuantiteByFournisseur(Long fournisseurId) {
        List<Produits> produits = produitsRepository.findByFournisseur_Id(fournisseurId);
        List<Disponibilite> dispos = disponibiliteRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Produits p : produits) {
            Map<String, Object> map = new HashMap<>();
            map.put("nom", p.getNom());
            map.put("uniteMesure", p.getUniteMesure());
            map.put("id", p.getId());
            // Chercher la disponibilité correspondante
            int quantite = 0;
            for (Disponibilite d : dispos) {
                if (d.getFournisseur().getId().equals(fournisseurId) && d.getProduit().getId().equals(p.getId())) {
                    quantite = d.getQuantite();
                    break;
                }
            }
            map.put("quantite", quantite);
            result.add(map);
        }
        return result;
    }
}
