package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ClientLoginDTO;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client inscrire(ClientDTO dto) throws Exception {
        if ((clientRepository.findByEmail(dto.getEmail())) != null) {
            throw new Exception("Email déjà utilisé");
        }

        File staticUploads = ResourceUtils
                .getFile(new File("src/main/resources/static/images/uploads").getAbsolutePath());

        if (!staticUploads.exists()) {
            staticUploads.mkdirs(); // juste au cas où
        }
        MultipartFile image = dto.getImage();
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File destinationFile = new File(staticUploads, fileName);
        image.transferTo(destinationFile);

                
        Client client = new Client();
        client.setNom(dto.getNom());
        client.setPrenoms(dto.getPrenoms());
        client.setDtn(dto.getDtn());
        client.setSexe(dto.getSexe());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setAdresse(dto.getAdresse());
        client.setMdp(dto.getMdp());
        client.setImage(fileName);

        return clientRepository.save(client);
    }

    public Client login(ClientLoginDTO dto) throws Exception {
        Optional<Client> clientOpt = clientRepository.findByEmailAndMdp(dto.getEmail(), dto.getMdp());
        if (clientOpt.isPresent()) {
            return clientOpt.get();
        } else {
            throw new Exception("Email ou mot de passe incorrect");
        }
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client getClientByID(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client enregistrerClient(Client client) {
        return clientRepository.save(client);
    }

    public void supprimerClient(Long id) {
        clientRepository.deleteById(id);
    }

    public boolean existeClient(Long id) {
        return clientRepository.existsById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
