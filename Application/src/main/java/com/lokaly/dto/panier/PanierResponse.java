package com.lokaly.dto.panier;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Builder
public class PanierResponse {
    private Long idPanier;
    private Long idClient;
    private LocalDate dateCreation;
    private List<LignePanierResponse> produits;
    private BigDecimal total;

    @Getter @Builder
    public static class LignePanierResponse {
        private Long idPanierProduit;
        private Long idProduit;
        private String nomProduit;
        private String image;
        private BigDecimal prixVente;
        private Integer quantite;
        private BigDecimal sousTotal;
    }
}
