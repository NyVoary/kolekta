package com.lokaly.dto.paiement;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class PaiementFournisseurRequest {
    @NotNull
    private Long idFournisseur;
    @NotNull @DecimalMin("0.01")
    private BigDecimal montant;
    @NotNull
    private Long idModePaiement;
    @NotNull
    private LocalDate datePaiement;
}
