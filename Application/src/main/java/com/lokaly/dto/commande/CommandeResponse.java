package com.lokaly.dto.commande;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Builder
public class CommandeResponse {
    private Long idCommande;
    private Long idClient;
    private String nomClient;
    private Long idEmploye;
    private String nomEmploye;
    private LocalDate dateCommande;
    private String status;
    private BigDecimal totalTTC;
    private List<LigneCommandeResponse> produits;

    @Getter @Builder
    public static class LigneCommandeResponse {
        private Long idProduit;
        private String nomProduit;
        private Integer quantite;
        private BigDecimal prixUnitaire;
        private BigDecimal sousTotal;
    }
}
