package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "assignations_taches")
@Getter @Setter @NoArgsConstructor
public class AssignationsTaches {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tache")
    private Long idTache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employes employe;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_assignation", nullable = false)
    private LocalDate dateAssignation = LocalDate.now();

    @Column(name = "date_echeance")
    private LocalDate dateEcheance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_priorite", nullable = false)
    private PrioritesTaches priorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_tache", nullable = false)
    private StatusTaches statusTache;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_assigne_par")
    private Employes assignePar;
}
