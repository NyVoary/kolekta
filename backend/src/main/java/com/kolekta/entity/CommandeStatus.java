package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "commande_status")
@Getter @Setter @NoArgsConstructor
public class CommandeStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_status")
    private Long idCommandeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employes employe;
}
