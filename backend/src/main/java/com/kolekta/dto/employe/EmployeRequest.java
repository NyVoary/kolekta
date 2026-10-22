package com.kolekta.dto.employe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class EmployeRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenoms;
    @NotNull
    private LocalDate dtn;
    private String email;
    private String telephone;
    private String adresse;
    private String poste;
    @NotBlank
    private String sexeCode;
    @NotNull
    private LocalDate dateEmbauche;
    @NotNull
    private BigDecimal salaire;
    @NotNull
    private Long idModePaiement;
}
