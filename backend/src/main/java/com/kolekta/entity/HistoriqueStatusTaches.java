package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "historique_status_taches")
@Getter @Setter @NoArgsConstructor
public class HistoriqueStatusTaches {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique_tache")
    private Long idHistoriqueTache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tache", nullable = false)
    private AssignationsTaches tache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_tache", nullable = false)
    private StatusTaches statusTache;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private Employes employe;
}
