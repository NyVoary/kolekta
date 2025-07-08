package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

public class HistoriqueAchat {
    private Long idCommande;
    private LocalDate dateCommande;
    private Double montantTotal;
    private List<String> produits;

    public HistoriqueAchat(Long idCommande, LocalDate dateCommande, Double montantTotal, List<String> produits) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.produits = produits;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public List<String> getProduits() {
        return produits;
    }

    public void setProduits(List<String> produits) {
        this.produits = produits;
    }
}
