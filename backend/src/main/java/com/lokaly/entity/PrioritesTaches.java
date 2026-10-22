package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "prioritestaches")
@Getter @Setter @NoArgsConstructor
public class PrioritesTaches {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_priorite")
    private Long idPriorite;

    @Column(nullable = false, length = 100)
    private String libelle;
}
