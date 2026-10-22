package com.kolekta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "status_paiement")
@Getter @Setter @NoArgsConstructor
public class StatusPaiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_paiement")
    private Integer idStatusPaiement;

    @Column(nullable = false, length = 50)
    private String libelle;
}
