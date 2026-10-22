package com.kolekta.dto.client;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter @Builder
public class ClientResponse {
    private Long idClient;
    private String nom;
    private String prenoms;
    private LocalDate dtn;
    private String email;
    private String telephone;
    private String adresse;
    private String sexe;
    private LocalDate dateCreation;
    private Boolean estActif;
    private LocalDate dateDesactivation;
}
