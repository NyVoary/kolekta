package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "modepaiement")
@Getter @Setter @NoArgsConstructor
public class ModePaiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mode")
    private Long idMode;

    @Column(nullable = false, unique = true, length = 100)
    private String libelle;
}
