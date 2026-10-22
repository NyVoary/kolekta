package com.kolekta.repository;

import com.kolekta.entity.Employes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployesRepository extends JpaRepository<Employes, Long> {
    List<Employes> findByDateFinEmbaucheIsNull();
    List<Employes> findByDateFinEmbaucheIsNotNull();
}
