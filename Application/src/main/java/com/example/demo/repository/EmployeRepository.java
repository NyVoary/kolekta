package com.example.demo.repository;

import com.example.demo.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional<Employe> findById(Long id);
    
    // 3. Version qui retourne directement l'entité ou null
    default Employe findByIdNullable(Long id) {
        return findById(id).orElse(null);
    }
    
    // 4. Version qui lève une exception si non trouvé
    default Employe findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> 
            new RuntimeException("Employé non trouvé avec l'ID: " + id));
    }
}