package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "produit_mois")
@Getter @Setter @NoArgsConstructor
public class ProduitMois {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit_mois")
    private Long idProduitMois;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;

    @Column(nullable = false)
    private Integer mois;
}
