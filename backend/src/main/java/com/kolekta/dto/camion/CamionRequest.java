package com.kolekta.dto.camion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CamionRequest {
    @NotBlank
    private String immatriculation;
    private String modele;
    @NotNull @Min(1)
    private Integer capaciteKg;
    @NotNull
    private Long idStatus;
}
