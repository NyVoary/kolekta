package com.example.demo.service;

import com.example.demo.dto.request.FilterCommandesRequest;
import com.example.demo.dto.response.CommandeClientResponse;
import java.util.List;

public interface CommandeService {
    List<CommandeClientResponse> getCommandesByClientWithFilters(Long clientId, FilterCommandesRequest filters);
}