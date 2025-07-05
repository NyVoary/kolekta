package com.example.demo.service;

import com.example.demo.entity.HistoriqueAchat;
import com.example.demo.entity.Client;
import com.example.demo.repository.HistoriqueAchatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoriqueAchatService {
    private final HistoriqueAchatRepository historiqueAchatRepository;

    public HistoriqueAchatService(HistoriqueAchatRepository historiqueAchatRepository) {
        this.historiqueAchatRepository = historiqueAchatRepository;
    }

    public List<HistoriqueAchat> getHistoriqueByClient(Client client) {
        return historiqueAchatRepository.findByClient(client);
    }
}
