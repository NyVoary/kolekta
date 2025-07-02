package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.CommandeProduit;

public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, Long> 
{

}
