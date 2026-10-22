package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "paiement_status")
@Getter @Setter @NoArgsConstructor
public class PaiementStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement_status")
    private Long idPaiementStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande_paiement", nullable = false)
    private CommandePaiement commandePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_paiement", nullable = false)
    private StatusPaiement statusPaiement;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employes employe;
}
