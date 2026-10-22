package com.lokaly.repository;

import com.lokaly.entity.HistoriqueStatusTaches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueStatusTachesRepository extends JpaRepository<HistoriqueStatusTaches, Long> {
    List<HistoriqueStatusTaches> findByTacheIdTacheOrderByDateChangementDesc(Long idTache);
}
