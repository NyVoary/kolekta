package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "paiement_fournisseur")
@Getter @Setter @NoArgsConstructor
public class PaiementFournisseur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement_fournisseur")
    private Long idPaiementFournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employes employe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fournisseur", nullable = false)
    private Fournisseurs fournisseur;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mode", nullable = false)
    private ModePaiement modePaiement;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;
}
