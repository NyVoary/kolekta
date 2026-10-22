package com.lokaly.dto.produit;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Builder
public class ProduitResponse {
    private Long idProduit;
    private String nom;
    private String description;
    private String categorie;
    private Long idCategorie;
    private String fournisseur;
    private Long idFournisseur;
    private String uniteMesure;
    private BigDecimal prixAchat;
    private BigDecimal prixVente;
    private String image;
    private LocalDate dateAjout;
    private List<Integer> moisDisponibles;
}
