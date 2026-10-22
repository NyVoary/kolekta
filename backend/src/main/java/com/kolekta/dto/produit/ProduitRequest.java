package com.kolekta.dto.produit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class ProduitRequest {
    @NotBlank
    private String nom;
    private String description;
    @NotNull
    private Long idCategorie;
    private Long idFournisseur;
    @NotBlank
    private String uniteMesure;
    @NotNull @DecimalMin("0.0")
    private BigDecimal prixAchat;
    @NotNull @DecimalMin("0.0")
    private BigDecimal prixVente;
    private String image;
    private List<Integer> moisDisponibles;
}
