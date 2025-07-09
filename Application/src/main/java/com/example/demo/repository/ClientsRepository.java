package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Clients;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
}