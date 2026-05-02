package com.lokaly.dto.fournisseur;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter @Builder
public class FournisseurResponse {
    private Long idFournisseur;
    private String nom;
    private String telephone;
    private String email;
    private String adresse;
    private LocalDate dateDesactivation;
}
