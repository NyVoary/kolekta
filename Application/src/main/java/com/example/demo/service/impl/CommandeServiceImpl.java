package com.example.demo.service.impl;

import com.example.demo.dto.request.FilterCommandesRequest;
import com.example.demo.dto.response.CommandeClientResponse;
import com.example.demo.entity.Commande;
import com.example.demo.mapper.CommandeMapper;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;

    @Autowired
    public CommandeServiceImpl(CommandeRepository commandeRepository, 
                             CommandeMapper commandeMapper) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
    }
    
    @Override
    public List<CommandeClientResponse> getCommandesByClientWithFilters(
        Long clientId, FilterCommandesRequest filters) {
        
        List<Commande> commandes = commandeRepository.findByClientIdWithFilters(
            clientId,
            filters.getStatut(),
            filters.getDateFrom(),
            filters.getDateTo()
        );
        
        return commandes.stream()
            .map(commandeMapper::toCommandeClientResponse)
            .collect(Collectors.toList());
    }
}