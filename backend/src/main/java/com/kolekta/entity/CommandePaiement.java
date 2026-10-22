package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "commande_paiement")
@Getter @Setter @NoArgsConstructor
public class CommandePaiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_paiement")
    private Long idCommandePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mode", nullable = false)
    private ModePaiement modePaiement;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employes employe;
}
