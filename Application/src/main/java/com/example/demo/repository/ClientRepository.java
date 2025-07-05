package com.example.demo.repository;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmailAndMdp(String email, String mdp);
    Client findByEmail(String email); 
}

=======
import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);

    Optional<Client> findByEmailAndMdp(String email, String mdp);
}
>>>>>>> Robel
