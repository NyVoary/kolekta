package com.lokaly.dto.camion;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CamionResponse {
    private Long idCamion;
    private String immatriculation;
    private String modele;
    private Integer capaciteKg;
    private String status;
}
