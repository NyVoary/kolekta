package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Table(name = "panier")
@Getter @Setter @NoArgsConstructor
public class Panier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_panier")
    private Long idPanier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Clients client;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation = LocalDate.now();
}
