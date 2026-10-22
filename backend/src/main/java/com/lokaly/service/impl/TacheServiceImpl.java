package com.lokaly.service.impl;

import com.lokaly.dto.tache.TacheRequest;
import com.lokaly.dto.tache.TacheResponse;
import com.lokaly.entity.*;
import com.lokaly.exception.ResourceNotFoundException;
import com.lokaly.repository.*;
import com.lokaly.service.TacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TacheServiceImpl implements TacheService {

    private final AssignationsTachesRepository tachesRepository;
    private final HistoriqueStatusTachesRepository historiqueRepository;
    private final EmployesRepository employesRepository;
    private final PrioritesTachesRepository prioritesRepository;
    private final StatusTachesRepository statusTachesRepository;

    @Override
    @Transactional
    public TacheResponse creer(TacheRequest request) {
        AssignationsTaches t = buildFromRequest(new AssignationsTaches(), request);
        AssignationsTaches saved = tachesRepository.save(t);
        enregistrerHistorique(saved, saved.getStatusTache(), null);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public TacheResponse modifier(Long id, TacheRequest request) {
        AssignationsTaches t = tachesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", id));
        buildFromRequest(t, request);
        return toResponse(tachesRepository.save(t));
    }

    @Override
    public TacheResponse trouverParId(Long id) {
        return toResponse(tachesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", id)));
    }

    @Override
    public List<TacheResponse> listerParEmploye(Long idEmploye) {
        return tachesRepository.findByEmployeIdEmploye(idEmploye).stream().map(this::toResponse).toList();
    }

    @Override
    public List<TacheResponse> listerTous() {
        return tachesRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void changerStatus(Long id, Long idStatusTache, Long idEmploye) {
        AssignationsTaches t = tachesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", id));
        StatusTaches status = statusTachesRepository.findById(idStatusTache)
                .orElseThrow(() -> new ResourceNotFoundException("StatusTache", idStatusTache));
        Employes employe = idEmploye != null ? employesRepository.findById(idEmploye)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", idEmploye)) : null;

        t.setStatusTache(status);
        tachesRepository.save(t);
        enregistrerHistorique(t, status, employe);
    }

    private AssignationsTaches buildFromRequest(AssignationsTaches t, TacheRequest request) {
        Employes employe = employesRepository.findById(request.getIdEmploye())
                .orElseThrow(() -> new ResourceNotFoundException("Employe", request.getIdEmploye()));
        PrioritesTaches priorite = prioritesRepository.findById(request.getIdPriorite())
                .orElseThrow(() -> new ResourceNotFoundException("Priorite", request.getIdPriorite()));
        StatusTaches status = statusTachesRepository.findById(request.getIdStatusTache())
                .orElseThrow(() -> new ResourceNotFoundException("StatusTache", request.getIdStatusTache()));
        Employes assignePar = request.getIdAssignePar() != null
                ? employesRepository.findById(request.getIdAssignePar())
                    .orElseThrow(() -> new ResourceNotFoundException("Employe", request.getIdAssignePar()))
                : null;

        t.setEmploye(employe);
        t.setTitre(request.getTitre());
        t.setDescription(request.getDescription());
        t.setDateAssignation(request.getDateAssignation());
        t.setDateEcheance(request.getDateEcheance());
        t.setPriorite(priorite);
        t.setStatusTache(status);
        t.setAssignePar(assignePar);
        return t;
    }

    private void enregistrerHistorique(AssignationsTaches tache, StatusTaches status, Employes employe) {
        HistoriqueStatusTaches h = new HistoriqueStatusTaches();
        h.setTache(tache);
        h.setStatusTache(status);
        h.setEmploye(employe);
        historiqueRepository.save(h);
    }

    private TacheResponse toResponse(AssignationsTaches t) {
        return TacheResponse.builder()
                .idTache(t.getIdTache())
                .idEmploye(t.getEmploye().getIdEmploye())
                .nomEmploye(t.getEmploye().getNom() + " " + t.getEmploye().getPrenoms())
                .titre(t.getTitre())
                .description(t.getDescription())
                .dateAssignation(t.getDateAssignation())
                .dateEcheance(t.getDateEcheance())
                .priorite(t.getPriorite().getLibelle())
                .statusTache(t.getStatusTache().getLibelle())
                .idAssignePar(t.getAssignePar() != null ? t.getAssignePar().getIdEmploye() : null)
                .nomAssignePar(t.getAssignePar() != null ? t.getAssignePar().getNom() + " " + t.getAssignePar().getPrenoms() : null)
                .build();
    }
}
