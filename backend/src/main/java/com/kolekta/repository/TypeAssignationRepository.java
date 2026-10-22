package com.kolekta.repository;

import com.kolekta.entity.TypeAssignation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeAssignationRepository extends JpaRepository<TypeAssignation, Long> {
    java.util.Optional<TypeAssignation> findByLibelle(String libelle);
}
