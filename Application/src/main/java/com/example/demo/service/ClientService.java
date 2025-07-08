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
import java.util.UUID;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ClientLoginDTO;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client inscrire(ClientDTO dto) throws Exception {
        // Vérification email existant
        if (clientRepository.findByEmail(dto.getEmail()) != null) {
            throw new Exception("Email déjà utilisé");
        }

        // Vérification de l'image
        if (dto.getImage() == null || dto.getImage().isEmpty()) {
            throw new Exception("Une image de profil est requise");
        }

        MultipartFile image = dto.getImage();
        String originalFilename = image.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new Exception("Le nom du fichier image est invalide");
        }

        // Création du répertoire de stockage si inexistant
        Path uploadDir = Paths.get("src/main/resources/static/images/uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Génération d'un nom de fichier unique
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;
        Path destinationFile = uploadDir.resolve(fileName);

        try {
            // Sauvegarde du fichier
            image.transferTo(destinationFile.toFile());
            System.out.println("Image enregistrée à : " + destinationFile.toAbsolutePath());
        } catch (IOException e) {
            throw new Exception("Erreur lors de l'enregistrement de l'image", e);
        }

        // Création et sauvegarde du client
        Client client = new Client();
        client.setNom(dto.getNom());
        client.setPrenoms(dto.getPrenoms());
        client.setDtn(dto.getDtn());
        client.setSexe(dto.getSexe());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setAdresse(dto.getAdresse());
        client.setDateCreation(dto.getDateCreation());
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
