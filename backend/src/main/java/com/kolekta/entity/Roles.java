package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "roles")
@Getter @Setter @NoArgsConstructor
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    @Column(nullable = false, unique = true, length = 20)
    private String libelle;
}
