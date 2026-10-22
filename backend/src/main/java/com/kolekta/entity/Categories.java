package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "categories")
@Getter @Setter @NoArgsConstructor
public class Categories {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private Long idCategorie;

    @Column(nullable = false, length = 100)
    private String libelle;
}
