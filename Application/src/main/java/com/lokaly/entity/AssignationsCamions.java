package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "assignations_camions")
@Getter @Setter @NoArgsConstructor
public class AssignationsCamions {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assignation_camion")
    private Long idAssignationCamion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_camion", nullable = false)
    private Camions camion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employes employe;

    @Column(name = "date_assignation", nullable = false)
    private LocalDate dateAssignation = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_assignation", nullable = false)
    private TypeAssignation typeAssignation;
}
