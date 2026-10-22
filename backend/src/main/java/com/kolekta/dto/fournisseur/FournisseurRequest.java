package com.kolekta.dto.fournisseur;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FournisseurRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String telephone;
    @Email
    private String email;
    private String adresse;
}
