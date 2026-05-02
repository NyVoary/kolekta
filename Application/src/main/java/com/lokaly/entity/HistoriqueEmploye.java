package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "historique_employe")
@Getter @Setter @NoArgsConstructor
public class HistoriqueEmploye {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique")
    private Long idHistorique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employes employe;

    @Column(name = "date_action", nullable = false)
    private LocalDateTime dateAction;

    @Column(nullable = false, length = 255)
    private String action;

    @Column(name = "cible_type", length = 100)
    private String cibleType;

    @Column(name = "id_cible")
    private Long idCible;

    @Column(columnDefinition = "TEXT")
    private String commentaire;
}
