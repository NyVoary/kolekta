package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "parametre")
@Getter @Setter @NoArgsConstructor
public class Parametre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametre")
    private Long idParametre;

    @Column(nullable = false, unique = true, length = 100)
    private String cle;

    @Column(columnDefinition = "TEXT")
    private String valeur;

    @Column(length = 20)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;
}
