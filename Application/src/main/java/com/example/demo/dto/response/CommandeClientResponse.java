package com.example.demo.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandeClientResponse {
    private Long idCommande;
    private String dateCommande;
    private Double montantTotal;
    private Double montantPaye;
    private Double montantRestant;
    private Double fraisLivraison;
    private String statutActuel;
    private String dateDernierStatut;
    private String adresseLivraison;
    private String villeLivraison;
}