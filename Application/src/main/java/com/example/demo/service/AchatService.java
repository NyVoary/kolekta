package com.example.demo.service;
import com.example.demo.entity.*;
import com.example.demo.repository.AchatRepository;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class AchatService {
    private final AchatRepository achatRepository;

    public AchatService(AchatRepository achatRepository) {
        this.achatRepository = achatRepository;
    }

    public List<Achat> getAchatsByClient(Client client) {
        return achatRepository.findByClient(client);
    }
}