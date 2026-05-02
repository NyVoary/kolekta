package com.lokaly.repository;

import com.lokaly.entity.Employes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployesRepository extends JpaRepository<Employes, Long> {
    List<Employes> findByActifTrue();
    List<Employes> findByRoleCode(String code);
}
