package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "panier_produits",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_panier", "id_produit"}))
@Getter @Setter @NoArgsConstructor
public class PanierProduits {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_panier_produit")
    private Long idPanierProduit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_panier", nullable = false)
    private Panier panier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;

    @Column(nullable = false)
    private Integer quantite;
}
