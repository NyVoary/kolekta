package com.kolekta.dto.employe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccesRequest {
    @NotNull
    private Long idEmploye;
    @NotBlank
    private String login;
    @NotBlank @Size(min = 8)
    private String motDePasse;
    @NotNull
    private Long idRole;
}
