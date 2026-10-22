package com.kolekta.service.impl;

import com.kolekta.dto.fournisseur.FournisseurRequest;
import com.kolekta.dto.fournisseur.FournisseurResponse;
import com.kolekta.entity.Fournisseurs;
import com.kolekta.exception.BadRequestException;
import com.kolekta.exception.ResourceNotFoundException;
import com.kolekta.repository.FournisseursRepository;
import com.kolekta.service.FournisseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseursRepository fournisseursRepository;

    @Override
    @Transactional
    public FournisseurResponse creer(FournisseurRequest request) {
        if (fournisseursRepository.existsByTelephone(request.getTelephone())) {
            throw new BadRequestException("Téléphone déjà utilisé");
        }
        if (request.getEmail() != null && fournisseursRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email déjà utilisé");
        }

        Fournisseurs f = new Fournisseurs();
        f.setNom(request.getNom());
        f.setTelephone(request.getTelephone());
        f.setEmail(request.getEmail());
        f.setAdresse(request.getAdresse());

        return toResponse(fournisseursRepository.save(f));
    }

    @Override
    @Transactional
    public FournisseurResponse modifier(Long id, FournisseurRequest request) {
        Fournisseurs f = fournisseursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", id));

        f.setNom(request.getNom());
        f.setTelephone(request.getTelephone());
        f.setEmail(request.getEmail());
        f.setAdresse(request.getAdresse());

        return toResponse(fournisseursRepository.save(f));
    }

    @Override
    public FournisseurResponse trouverParId(Long id) {
        return toResponse(fournisseursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", id)));
    }

    @Override
    public List<FournisseurResponse> listerActifs() {
        return fournisseursRepository.findByDateDesactivationIsNull().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void desactiver(Long id) {
        Fournisseurs f = fournisseursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", id));
        f.setDateDesactivation(LocalDate.now());
        fournisseursRepository.save(f);
    }

    private FournisseurResponse toResponse(Fournisseurs f) {
        return FournisseurResponse.builder()
                .idFournisseur(f.getIdFournisseur())
                .nom(f.getNom())
                .telephone(f.getTelephone())
                .email(f.getEmail())
                .adresse(f.getAdresse())
                .dateDesactivation(f.getDateDesactivation())
                .build();
    }
}
