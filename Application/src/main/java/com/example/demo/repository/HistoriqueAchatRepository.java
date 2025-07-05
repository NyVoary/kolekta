package com.example.demo.repository;

import com.example.demo.entity.HistoriqueAchat;
import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoriqueAchatRepository extends JpaRepository<HistoriqueAchat, Long> {
    List<HistoriqueAchat> findByClient(Client client);
}
