package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PaiementStatus;

@Repository
public interface PaiementStatusRepository extends JpaRepository<PaiementStatus, Integer> {
}