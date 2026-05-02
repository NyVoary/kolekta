package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "disponibilites_fournisseur")
@Getter @Setter @NoArgsConstructor
public class DisponibilitesFournisseur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispo")
    private Long idDispo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fournisseur", nullable = false)
    private Fournisseurs fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;

    @Column(name = "quantite_disponible", nullable = false)
    private Integer quantiteDisponible = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employes employe;
}
