package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Client;
import com.example.demo.entity.Commandes;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commandes, Long> {
    @Query("SELECT c FROM Commandes c WHERE c.client.id = :clientId")
    List<Commandes> findByClient(@Param("clientId") Long id);
}
