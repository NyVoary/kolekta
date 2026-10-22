package com.kolekta.dto.commande;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CommandeRequest {
    @NotNull
    private Long idClient;

    @NotEmpty
    private List<LigneCommande> produits;

    @Getter @Setter
    public static class LigneCommande {
        @NotNull
        private Long idProduit;
        @NotNull @Min(1)
        private Integer quantite;
    }
}
