package com.lokaly.service.impl;

import com.lokaly.dto.commande.CommandeRequest;
import com.lokaly.dto.commande.CommandeResponse;
import com.lokaly.entity.*;
import com.lokaly.exception.ResourceNotFoundException;
import com.lokaly.repository.*;
import com.lokaly.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandesRepository commandesRepository;
    private final CommandeProduitsRepository commandeProduitsRepository;
    private final CommandeStatusRepository commandeStatusRepository;
    private final ClientsRepository clientsRepository;
    private final ProduitsRepository produitsRepository;
    private final StatusRepository statusRepository;
    private final EmployesRepository employesRepository;

    private static final String STATUS_EN_ATTENTE = "En attente";

    @Override
    @Transactional
    public CommandeResponse creer(CommandeRequest request) {
        Clients client = clientsRepository.findById(request.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client", request.getIdClient()));

        Status statusInitial = statusRepository.findByLibelle(STATUS_EN_ATTENTE)
                .orElseThrow(() -> new ResourceNotFoundException("Status", 0L));

        Commandes commande = new Commandes();
        commande.setClient(client);
        commande.setStatus(statusInitial);
        Commandes saved = commandesRepository.save(commande);

        for (CommandeRequest.LigneCommande ligne : request.getProduits()) {
            Produits produit = produitsRepository.findById(ligne.getIdProduit())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit", ligne.getIdProduit()));

            CommandeProduits cp = new CommandeProduits();
            cp.setCommande(saved);
            cp.setProduit(produit);
            cp.setQuantite(ligne.getQuantite());
            cp.setPrixUnitaire(produit.getPrixVente());
            commandeProduitsRepository.save(cp);
        }

        CommandeStatus cs = new CommandeStatus();
        cs.setCommande(saved);
        cs.setStatus(statusInitial);
        commandeStatusRepository.save(cs);

        return toResponse(saved);
    }

    @Override
    public CommandeResponse trouverParId(Long id) {
        return toResponse(commandesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", id)));
    }

    @Override
    public List<CommandeResponse> listerParClient(Long idClient) {
        return commandesRepository.findByClientIdClient(idClient).stream().map(this::toResponse).toList();
    }

    @Override
    public List<CommandeResponse> listerTous() {
        return commandesRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void changerStatus(Long id, Long idStatus, Long idEmploye) {
        Commandes commande = commandesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande", id));
        Status status = statusRepository.findById(idStatus)
                .orElseThrow(() -> new ResourceNotFoundException("Status", idStatus));
        Employes employe = null;
        if (idEmploye != null) {
            employe = employesRepository.findById(idEmploye)
                    .orElseThrow(() -> new ResourceNotFoundException("Employe", idEmploye));
        }

        commande.setStatus(status);
        commandesRepository.save(commande);

        CommandeStatus cs = new CommandeStatus();
        cs.setCommande(commande);
        cs.setStatus(status);
        cs.setEmploye(employe);
        commandeStatusRepository.save(cs);
    }

    private CommandeResponse toResponse(Commandes c) {
        List<CommandeProduits> lignes = commandeProduitsRepository.findByCommandeIdCommande(c.getIdCommande());

        BigDecimal total = lignes.stream()
                .map(l -> l.getPrixUnitaire().multiply(BigDecimal.valueOf(l.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CommandeResponse.LigneCommandeResponse> produits = lignes.stream()
                .map(l -> CommandeResponse.LigneCommandeResponse.builder()
                        .idProduit(l.getProduit().getIdProduit())
                        .nomProduit(l.getProduit().getNom())
                        .quantite(l.getQuantite())
                        .prixUnitaire(l.getPrixUnitaire())
                        .sousTotal(l.getPrixUnitaire().multiply(BigDecimal.valueOf(l.getQuantite())))
                        .build())
                .toList();

        return CommandeResponse.builder()
                .idCommande(c.getIdCommande())
                .idClient(c.getClient().getIdClient())
                .nomClient(c.getClient().getNom() + " " + c.getClient().getPrenoms())
                .idEmploye(c.getEmploye() != null ? c.getEmploye().getIdEmploye() : null)
                .nomEmploye(c.getEmploye() != null ? c.getEmploye().getNom() + " " + c.getEmploye().getPrenoms() : null)
                .dateCommande(c.getDateCommande())
                .status(c.getStatus().getLibelle())
                .totalTTC(total)
                .produits(produits)
                .build();
    }
}
