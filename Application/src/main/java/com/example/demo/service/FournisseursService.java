package com.example.demo.service;

import com.example.demo.entity.Disponibilite;
import com.example.demo.entity.Fournisseurs;
import com.example.demo.entity.Produits;
import com.example.demo.repository.DisponibiliteRepository;
import com.example.demo.repository.FornisseursRepository;
import com.example.demo.repository.ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FournisseursService {
    @Autowired
    private FornisseursRepository fournisseursRepository;

    @Autowired
    private ProduitsRepository produitsRepository;

    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

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
        List<Produits> produits = produitsRepository.findByFournisseurs_Id(fournisseurId);
        System.out.println("[DEBUG] Produits pour fournisseur " + fournisseurId + " : " + produits.size());
        for (Produits p : produits) {
            System.out.println("[DEBUG] Produit: id=" + p.getId() + ", nom=" + p.getNom());
        }
        return produits;
    }

    public Disponibilite saveDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }
}
