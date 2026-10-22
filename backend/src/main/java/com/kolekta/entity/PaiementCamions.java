package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "paiement_camions")
@Getter @Setter @NoArgsConstructor
public class PaiementCamions {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement_camion")
    private Long idPaiementCamion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_assignation_camion", nullable = false)
    private AssignationsCamions assignationCamion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employes employe;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mode", nullable = false)
    private ModePaiement modePaiement;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;
}
