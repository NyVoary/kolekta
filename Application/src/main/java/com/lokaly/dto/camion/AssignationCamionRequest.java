package com.lokaly.dto.camion;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AssignationCamionRequest {
    @NotNull
    private Long idCamion;
    @NotNull
    private Long idEmploye;
    @NotNull
    private LocalDate dateAssignation;
    @NotNull
    private Long idTypeAssignation;
}
