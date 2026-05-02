package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "typeassignation")
@Getter @Setter @NoArgsConstructor
public class TypeAssignation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private Long idType;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;
}
