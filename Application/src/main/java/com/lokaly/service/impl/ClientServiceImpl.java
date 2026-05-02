package com.lokaly.service.impl;

import com.lokaly.dto.client.ClientRequest;
import com.lokaly.dto.client.ClientResponse;
import com.lokaly.entity.Clients;
import com.lokaly.entity.Sexe;
import com.lokaly.exception.BadRequestException;
import com.lokaly.exception.ResourceNotFoundException;
import com.lokaly.repository.ClientsRepository;
import com.lokaly.repository.SexeRepository;
import com.lokaly.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientsRepository clientsRepository;
    private final SexeRepository sexeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ClientResponse inscrire(ClientRequest request) {
        if (clientsRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email déjà utilisé");
        }
        if (clientsRepository.existsByTelephone(request.getTelephone())) {
            throw new BadRequestException("Téléphone déjà utilisé");
        }

        Sexe sexe = sexeRepository.findById(request.getSexeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Sexe", 0L));

        Clients client = new Clients();
        client.setNom(request.getNom());
        client.setPrenoms(request.getPrenoms());
        client.setDtn(request.getDtn());
        client.setEmail(request.getEmail());
        client.setTelephone(request.getTelephone());
        client.setAdresse(request.getAdresse());
        client.setSexe(sexe);
        client.setMdp(passwordEncoder.encode(request.getMdp()));

        return toResponse(clientsRepository.save(client));
    }

    @Override
    @Transactional
    public ClientResponse modifier(Long id, ClientRequest request) {
        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));

        Sexe sexe = sexeRepository.findById(request.getSexeCode())
                .orElseThrow(() -> new ResourceNotFoundException("Sexe", 0L));

        client.setNom(request.getNom());
        client.setPrenoms(request.getPrenoms());
        client.setDtn(request.getDtn());
        client.setTelephone(request.getTelephone());
        client.setAdresse(request.getAdresse());
        client.setSexe(sexe);

        return toResponse(clientsRepository.save(client));
    }

    @Override
    public ClientResponse trouverParId(Long id) {
        return toResponse(clientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id)));
    }

    @Override
    public List<ClientResponse> listerActifs() {
        return clientsRepository.findAll().stream()
                .filter(c -> Boolean.TRUE.equals(c.getEstActif()))
                .map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void desactiver(Long id) {
        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
        client.setEstActif(false);
        client.setDateDesactivation(LocalDate.now());
        clientsRepository.save(client);
    }

    private ClientResponse toResponse(Clients c) {
        return ClientResponse.builder()
                .idClient(c.getIdClient())
                .nom(c.getNom())
                .prenoms(c.getPrenoms())
                .dtn(c.getDtn())
                .email(c.getEmail())
                .telephone(c.getTelephone())
                .adresse(c.getAdresse())
                .sexe(c.getSexe() != null ? c.getSexe().getLibelle() : null)
                .dateCreation(c.getDateCreation())
                .estActif(c.getEstActif())
                .dateDesactivation(c.getDateDesactivation())
                .build();
    }
}
