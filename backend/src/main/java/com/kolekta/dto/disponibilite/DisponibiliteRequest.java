package com.kolekta.dto.disponibilite;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DisponibiliteRequest {
    @NotNull
    private Long idFournisseur;
    @NotNull
    private Long idProduit;
    @NotNull @Min(0)
    private Integer quantiteDisponible;
}
