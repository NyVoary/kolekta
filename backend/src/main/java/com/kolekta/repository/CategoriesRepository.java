package com.kolekta.repository;

import com.kolekta.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Optional<Categories> findByLibelleIgnoreCase(String libelle);
    boolean existsByLibelleIgnoreCase(String libelle);
}
