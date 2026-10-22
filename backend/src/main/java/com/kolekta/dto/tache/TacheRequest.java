package com.kolekta.dto.tache;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TacheRequest {
    @NotNull
    private Long idEmploye;
    @NotBlank
    private String titre;
    private String description;
    @NotNull
    private LocalDate dateAssignation;
    private LocalDate dateEcheance;
    @NotNull
    private Long idPriorite;
    @NotNull
    private Long idStatusTache;
    private Long idAssignePar;
}
