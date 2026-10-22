package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "clients")
@Getter @Setter @NoArgsConstructor
public class Clients {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenoms;

    @Column(nullable = false)
    private LocalDate dtn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexe")
    private Sexe sexe;

    @Column(unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(nullable = false, length = 255)
    private String adresse;

    @Column(length = 255)
    private String mdp;

    @Column(length = 100)
    private String image;

    @Column(name = "date_creation")
    private LocalDate dateCreation = LocalDate.now();

    @Column(name = "estactif", nullable = false)
    private Boolean estActif = true;

    @Column(name = "date_desactivation")
    private LocalDate dateDesactivation;
}
