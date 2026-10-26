package com.kolekta.service.impl;

import com.kolekta.entity.JetonRevoque;
import com.kolekta.repository.JetonRevoqueRepository;
import com.kolekta.service.JetonRevoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JetonRevoqueServiceImpl implements JetonRevoqueService {

    private final JetonRevoqueRepository repository;

    @Override
    @Transactional
    public void revoquer(String jti, Instant expireLe) {
        if (!repository.existsById(jti)) {
            repository.save(new JetonRevoque(jti, expireLe));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estRevoque(String jti) {
        // Un jeton sans jti (émis avant l'introduction de la révocation) ne peut pas être révoqué
        return jti != null && repository.existsById(jti);
    }

    @Override
    @Transactional
    public int purgerExpires() {
        return repository.supprimerExpiresAvant(Instant.now());
    }
}
