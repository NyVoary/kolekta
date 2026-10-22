package com.kolekta.dto.employe;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Builder
public class EmployeResponse {
    private Long idEmploye;
    private String nom;
    private String prenoms;
    private LocalDate dtn;
    private String email;
    private String telephone;
    private String adresse;
    private String poste;
    private String sexe;
    private LocalDate dateEmbauche;
    private LocalDate dateFinEmbauche;
    private BigDecimal salaire;
    private String modePaiement;
}
