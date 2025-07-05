package com.example.demo.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.CommandeProduit;

public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, Long> 
{

}
=======
import com.example.demo.entity.CommandeProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, Long> {
    List<CommandeProduit> findByCommandeId(Long commandeId);
}
>>>>>>> Robel
