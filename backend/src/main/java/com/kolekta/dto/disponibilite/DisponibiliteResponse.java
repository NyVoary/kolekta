package com.kolekta.dto.disponibilite;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class DisponibiliteResponse {
    private Long idDispo;
    private Long idFournisseur;
    private String nomFournisseur;
    private Long idProduit;
    private String nomProduit;
    private Integer quantiteDisponible;
}
