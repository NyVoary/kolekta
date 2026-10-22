package com.kolekta.repository;

import com.kolekta.entity.PaiementEmploye;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementEmployeRepository extends JpaRepository<PaiementEmploye, Long> {
    List<PaiementEmploye> findByEmployeIdEmploye(Long idEmploye);
    boolean existsByEmployeIdEmployeAndMoisAndAnnee(Long idEmploye, Integer mois, Integer annee);
}
