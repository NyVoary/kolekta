package com.lokaly.dto.tache;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter @Builder
public class TacheResponse {
    private Long idTache;
    private Long idEmploye;
    private String nomEmploye;
    private String titre;
    private String description;
    private LocalDate dateAssignation;
    private LocalDate dateEcheance;
    private String priorite;
    private String statusTache;
    private Long idAssignePar;
    private String nomAssignePar;
}
