package com.example.demo.mapper;

import com.example.demo.dto.response.CommandeClientResponse;
import com.example.demo.entity.Commandes;
import com.example.demo.entity.CommandePaiement;
import com.example.demo.entity.CommandeStatus;
import org.mapstruct.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommandeMapper {
    @Mapping(target = "idCommande", source = "id")
    @Mapping(target = "dateCommande", expression = "java(formatDate(commande.getDateCommande()))")
    @Mapping(target = "statutActuel", source = "commandeStatusList", qualifiedByName = "getLatestStatus")
    @Mapping(target = "dateDernierStatut", expression = "java(formatDate(getLatestStatusDate(commande.getCommandeStatusList())))")
    @Mapping(target = "montantPaye", source = "paiements", qualifiedByName = "calculateMontantPaye")
    @Mapping(target = "montantRestant", expression = "java(commande.getMontantTotal() - calculateMontantPaye(commande.getPaiements()))")

    CommandeClientResponse toCommandeClientResponse(Commandes commande);

    @Named("getLatestStatus")
    default String getLatestStatus(List<CommandeStatus> statusList) {
        return Optional.ofNullable(statusList)
                .flatMap(list -> list.stream()
                        .max(Comparator.comparing(CommandeStatus::getDateChangement))
                        .map(status -> status.getStatus() != null ? 
                             status.getStatus().getLibelle() : "NON_DEFINI"))
                .orElse("NON_DEFINI");
    }

    @Named("calculateMontantPaye")
    default Double calculateMontantPaye(List<CommandePaiement> paiements) {
        if (paiements == null || paiements.isEmpty()) {
            return 0.0;
        }
        return paiements.stream()
                .map(CommandePaiement::getMontant)
                .reduce(0.0, Double::sum);
    }

    default LocalDate getLatestStatusDate(List<CommandeStatus> statusList) {
        return Optional.ofNullable(statusList)
                .flatMap(list -> list.stream()
                        .max(Comparator.comparing(CommandeStatus::getDateChangement))
                        .map(CommandeStatus::getDateChangement))
                .orElse(null);
    }

    default String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}