package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "commandes_camions")
@Getter @Setter @NoArgsConstructor
public class CommandesCamions {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_camion")
    private Long idCommandeCamion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_assignation_camion", nullable = false)
    private AssignationsCamions assignationCamion;

    @Column(name = "date_livraison_prevue")
    private LocalDate dateLivraisonPrevue;
}
