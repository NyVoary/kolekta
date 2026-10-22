package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "sexe")
@Getter @Setter @NoArgsConstructor
public class Sexe {
    @Id
    @Column(name = "code", length = 1)
    private String code;

    @Column(nullable = false, unique = true, length = 20)
    private String libelle;
}
