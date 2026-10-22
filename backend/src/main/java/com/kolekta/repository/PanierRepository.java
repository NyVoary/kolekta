package com.kolekta.repository;

import com.kolekta.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Long> {
    List<Panier> findByClientIdClient(Long idClient);
    Optional<Panier> findTopByClientIdClientOrderByDateCreationDesc(Long idClient);
}
