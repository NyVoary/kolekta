package com.lokaly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "acces")
@Getter @Setter @NoArgsConstructor
public class Acces {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acces")
    private Long idAcces;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employes employe;

    @Column(name = "nom_utilisateur", nullable = false, unique = true, length = 50)
    private String nomUtilisateur;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", nullable = false)
    private Roles role;
}
