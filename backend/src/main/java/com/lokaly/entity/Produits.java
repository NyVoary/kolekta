package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "produits")
@Getter @Setter @NoArgsConstructor
public class Produits {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fournisseur")
    private Fournisseurs fournisseur;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categories categorie;

    @Column(name = "unite_mesure", nullable = false, length = 100)
    private String uniteMesure;

    @Column(name = "prix_achat", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixAchat;

    @Column(name = "prix_vente", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixVente;

    @Column(name = "date_ajout", nullable = false)
    private LocalDate dateAjout = LocalDate.now();

    @Column(length = 100)
    private String image;
}
