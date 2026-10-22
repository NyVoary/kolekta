package com.kolekta.repository;

import com.kolekta.entity.Fournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FournisseursRepository extends JpaRepository<Fournisseurs, Long> {
    Optional<Fournisseurs> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
    List<Fournisseurs> findByDateDesactivationIsNull();
}
