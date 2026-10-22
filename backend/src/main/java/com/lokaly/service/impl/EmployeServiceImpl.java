package com.lokaly.service.impl;

import com.lokaly.dto.employe.AccesRequest;
import com.lokaly.dto.employe.EmployeRequest;
import com.lokaly.dto.employe.EmployeResponse;
import com.lokaly.entity.*;
import com.lokaly.exception.BadRequestException;
import com.lokaly.exception.ResourceNotFoundException;
import com.lokaly.repository.*;
import com.lokaly.service.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements EmployeService {

    private final EmployesRepository employesRepository;
    private final AccesRepository accesRepository;
    private final SexeRepository sexeRepository;
    private final ModePaiementRepository modePaiementRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public EmployeResponse creer(EmployeRequest request) {
        Sexe sexe = sexeRepository.findById(request.getSexeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Sexe", 0L));
        ModePaiement mode = modePaiementRepository.findById(request.getIdModePaiement())
                .orElseThrow(() -> new ResourceNotFoundException("ModePaiement", request.getIdModePaiement()));

        Employes employe = new Employes();
        employe.setNom(request.getNom());
        employe.setPrenoms(request.getPrenoms());
        employe.setDtn(request.getDtn());
        employe.setEmail(request.getEmail());
        employe.setTelephone(request.getTelephone());
        employe.setAdresse(request.getAdresse());
        employe.setPoste(request.getPoste());
        employe.setSexe(sexe);
        employe.setDateEmbauche(request.getDateEmbauche());
        employe.setSalaire(request.getSalaire());
        employe.setModePaiement(mode);

        return toResponse(employesRepository.save(employe));
    }

    @Override
    @Transactional
    public EmployeResponse modifier(Long id, EmployeRequest request) {
        Employes employe = employesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));

        Sexe sexe = sexeRepository.findById(request.getSexeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Sexe", 0L));
        ModePaiement mode = modePaiementRepository.findById(request.getIdModePaiement())
                .orElseThrow(() -> new ResourceNotFoundException("ModePaiement", request.getIdModePaiement()));

        employe.setNom(request.getNom());
        employe.setPrenoms(request.getPrenoms());
        employe.setDtn(request.getDtn());
        employe.setEmail(request.getEmail());
        employe.setTelephone(request.getTelephone());
        employe.setAdresse(request.getAdresse());
        employe.setPoste(request.getPoste());
        employe.setSexe(sexe);
        employe.setDateEmbauche(request.getDateEmbauche());
        employe.setSalaire(request.getSalaire());
        employe.setModePaiement(mode);

        return toResponse(employesRepository.save(employe));
    }

    @Override
    public EmployeResponse trouverParId(Long id) {
        return toResponse(employesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id)));
    }

    @Override
    public List<EmployeResponse> listerActifs() {
        return employesRepository.findByDateFinEmbaucheIsNull().stream().map(this::toResponse).toList();
    }

    @Override
    public List<EmployeResponse> listerTous() {
        return employesRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void desactiver(Long id) {
        Employes employe = employesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
        employe.setDateFinEmbauche(LocalDate.now());
        employesRepository.save(employe);
    }

    @Override
    @Transactional
    public void creerAcces(AccesRequest request) {
        Employes employe = employesRepository.findById(request.getIdEmploye())
                .orElseThrow(() -> new ResourceNotFoundException("Employe", request.getIdEmploye()));

        if (accesRepository.existsByNomUtilisateur(request.getLogin())) {
            throw new BadRequestException("Ce login est déjà utilisé");
        }

        Roles role = rolesRepository.findById(request.getIdRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role", request.getIdRole()));

        Acces acces = new Acces();
        acces.setEmploye(employe);
        acces.setNomUtilisateur(request.getLogin());
        acces.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        acces.setRole(role);
        accesRepository.save(acces);
    }

    private EmployeResponse toResponse(Employes e) {
        return EmployeResponse.builder()
                .idEmploye(e.getIdEmploye())
                .nom(e.getNom())
                .prenoms(e.getPrenoms())
                .dtn(e.getDtn())
                .email(e.getEmail())
                .telephone(e.getTelephone())
                .adresse(e.getAdresse())
                .poste(e.getPoste())
                .sexe(e.getSexe() != null ? e.getSexe().getLibelle() : null)
                .dateEmbauche(e.getDateEmbauche())
                .dateFinEmbauche(e.getDateFinEmbauche())
                .salaire(e.getSalaire())
                .modePaiement(e.getModePaiement() != null ? e.getModePaiement().getLibelle() : null)
                .build();
    }
}
