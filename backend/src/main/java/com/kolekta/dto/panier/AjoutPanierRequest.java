package com.kolekta.dto.panier;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AjoutPanierRequest {
    @NotNull
    private Long idProduit;
    @NotNull @Min(1)
    private Integer quantite;
}
