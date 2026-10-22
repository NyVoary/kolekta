package com.kolekta.repository;

import com.kolekta.entity.HistoriqueEmploye;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueEmployeRepository extends JpaRepository<HistoriqueEmploye, Long> {
    List<HistoriqueEmploye> findByEmployeIdEmployeOrderByDateChangementDesc(Long idEmploye);
}
