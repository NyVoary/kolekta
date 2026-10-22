package com.kolekta.dto.paiement;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class PaiementEmployeRequest {
    @NotNull
    private Long idEmploye;
    @NotNull @DecimalMin("0.01")
    private BigDecimal montant;
    @NotNull @Min(1) @Max(12)
    private Integer mois;
    @NotNull @Min(2000)
    private Integer annee;
    @NotNull
    private Long idModePaiement;
    @NotNull
    private LocalDate datePaiement;
}
