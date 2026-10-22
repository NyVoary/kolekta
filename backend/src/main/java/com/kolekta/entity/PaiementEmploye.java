package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "paiement_employe")
@Getter @Setter @NoArgsConstructor
public class PaiementEmploye {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement_employe")
    private Long idPaiementEmploye;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employes employe;

    @Column(nullable = false)
    private Integer mois;

    @Column(nullable = false)
    private Integer annee;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;
}
