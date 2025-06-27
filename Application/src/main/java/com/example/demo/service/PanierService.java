package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Panier;
import com.example.demo.entity.PanierProduit;
import com.example.demo.entity.Produit;
import com.example.demo.repository.PanierProduitRepository;
import com.example.demo.repository.PanierRepository;
import com.example.demo.repository.ProduitRepository;

@Service
@Transactional
public class PanierService {
    
    @Autowired
    private PanierRepository panierRepository;
    
    @Autowired
    private PanierProduitRepository panierProduitRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    private static final BigDecimal TAUX_TVA = new BigDecimal("0.20"); 
    private static final BigDecimal FRAIS_LIVRAISON = new BigDecimal("5000.00"); 

    public Panier creerPanierPourClient(Long idClient) {
        if (panierRepository.existsByIdClient(idClient)) {
            return panierRepository.findByIdClient(idClient).orElse(null);
        }
        
        Panier nouveauPanier = new Panier(idClient, LocalDate.now());
        return panierRepository.save(nouveauPanier);
    }
    
    public Optional<Panier> getPanierAvecProduits(Long idClient) {
        return panierRepository.findByIdClientWithProduits(idClient);
    }
    
    public void ajouterProduitAuPanier(Long idClient, Long idProduit, Integer quantite) {
        Panier panier = panierRepository.findByIdClient(idClient)
                .orElseGet(() -> creerPanierPourClient(idClient));
        
        Optional<Produit> produitOpt = produitRepository.findById(idProduit);
        if (!produitOpt.isPresent()) {
            throw new RuntimeException("Produit non trouvé");
        }
        
        Produit produit = produitOpt.get();
        
        
        Optional<PanierProduit> panierProduitExistant = 
                panierProduitRepository.findByPanierIdPanierAndProduitIdProduit(panier.getIdPanier(), idProduit);
        
        if (panierProduitExistant.isPresent()) {
            
            PanierProduit pp = panierProduitExistant.get();
            pp.setQuantite(pp.getQuantite() + quantite);
            panierProduitRepository.save(pp);
        } else {
          
            PanierProduit nouveauPanierProduit = new PanierProduit(
                    panier, produit, quantite, produit.getPrixVente()
            );
            panierProduitRepository.save(nouveauPanierProduit);
        }
    }
    
    public void mettreAJourQuantite(Long idClient, Long idProduit, Integer nouvelleQuantite) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (!panierOpt.isPresent()) {
            throw new RuntimeException("Panier non trouvé");
        }
        
        Optional<PanierProduit> panierProduitOpt = 
                panierProduitRepository.findByPanierIdPanierAndProduitIdProduit(
                        panierOpt.get().getIdPanier(), idProduit);
        
        if (panierProduitOpt.isPresent()) {
            if (nouvelleQuantite <= 0) {
                supprimerProduitDuPanier(idClient, idProduit);
            } else {
                PanierProduit pp = panierProduitOpt.get();
                pp.setQuantite(nouvelleQuantite);
                panierProduitRepository.save(pp);
            }
        }
    }
    
    public void supprimerProduitDuPanier(Long idClient, Long idProduit) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (panierOpt.isPresent()) {
            panierProduitRepository.deleteByPanierIdAndProduitId(
                    panierOpt.get().getIdPanier(), idProduit);
        }
    }

    public BigDecimal calculerMontantHT(Panier panier) {
        return panier.getPanierProduits().stream()
                .map(PanierProduit::getSousTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculerTVA(BigDecimal montantHT) {
        return montantHT.multiply(TAUX_TVA);
    }

    public BigDecimal calculerMontantTTC(BigDecimal montantHT) {
        return montantHT.add(calculerTVA(montantHT));
    }

    public BigDecimal calculerMontantTotal(BigDecimal montantTTC) {
        return montantTTC.add(FRAIS_LIVRAISON);
    }

    public void viderPanier(Long idClient) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (panierOpt.isPresent()) {
            Long idPanier = panierOpt.get().getIdPanier();
            panierProduitRepository.deleteByPanierId(idPanier);
        }
    }

    public Long compterArticlesPanier(Long idClient) {
        Optional<Panier> panierOpt = panierRepository.findByIdClient(idClient);
        if (panierOpt.isPresent()) {    
            return panierProduitRepository.countByPanierId(panierOpt.get().getIdPanier());
        }
        return 0L;
    }

    public BigDecimal getFraisLivraison() {
        return FRAIS_LIVRAISON;
    }
}