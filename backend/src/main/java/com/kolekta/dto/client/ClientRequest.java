package com.kolekta.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ClientRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenoms;
    @NotNull
    private LocalDate dtn;
    @Email @NotBlank
    private String email;
    @NotBlank
    private String telephone;
    @NotBlank
    private String adresse;
    @NotBlank
    private String sexeCode;
    @NotBlank @Size(min = 8)
    private String mdp;
}
