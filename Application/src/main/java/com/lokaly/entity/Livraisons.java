package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "livraisons")
@Getter @Setter @NoArgsConstructor
public class Livraisons {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livraison")
    private Long idLivraison;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_assignation_camion")
    private AssignationsCamions assignationCamion;

    @Column(name = "date_livraison")
    private LocalDate dateLivraison;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
