package com.example.demo.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandeClientResponse {
    private Long idCommande;
    private String dateCommande;
    private BigDecimal montantTotal;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private BigDecimal fraisLivraison;
    private String statutActuel;
    private String dateDernierStatut;
    private String adresseLivraison;
    private String villeLivraison;
}