package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "camions")
@Getter @Setter @NoArgsConstructor
public class Camions {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camion")
    private Long idCamion;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    @Column(length = 100)
    private String modele;

    @Column(name = "capacite_kg", nullable = false)
    private Integer capaciteKg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;
}
