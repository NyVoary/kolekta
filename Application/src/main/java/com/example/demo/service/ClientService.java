package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
// import java.nio.file.Paths;
import java.nio.file.Paths;
import java.util.Optional;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ClientLoginDTO;

import com.example.demo.entity.Clients;
import com.example.demo.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientsRepository;

    public Clients inscrire(ClientDTO dto) throws Exception {
        if ((clientsRepository.findByEmail(dto.getEmail())) != null) {
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
        
        if (fileName.endsWith("_")) {
            throw new Exception("Veuillez entrez le photo de votre employe");
        }
        image.transferTo(destinationFile);
                
        Clients clients = new Clients();
        clients.setNom(dto.getNom());
        clients.setPrenoms(dto.getPrenoms());
        clients.setDtn(dto.getDtn());
        clients.setSexe(dto.getSexe());
        clients.setEmail(dto.getEmail());
        clients.setTelephone(dto.getTelephone());
        clients.setAdresse(dto.getAdresse());
        clients.setMdp(dto.getMdp());
        clients.setImage(fileName);

        return clientsRepository.save(clients);
    }

    public Clients login(ClientLoginDTO dto) throws Exception {
        Optional<Clients> clientOpt = clientsRepository.findByEmailAndMdp(dto.getEmail(), dto.getMdp());
        if (clientOpt.isPresent()) {
            return clientOpt.get();
        } else {
            throw new Exception("Email ou mot de passe incorrect");
        }
    }

    public Optional<Clients> getClientById(Long id) {
        return clientsRepository.findById(id);
    }

    public Clients getClientByID(Long id) {
        return clientsRepository.findById(id).orElse(null);
    }

    public Clients enregistrerClient(Clients client) {
        return clientsRepository.save(client);
    }

    public void supprimerClient(Long id) {
        clientsRepository.deleteById(id);
    }

    public boolean existeClient(Long id) {
        return clientsRepository.existsById(id);
    }
    public List<Client> getAllClients() {
        return clientsRepository.findAll();
    }    
    
    public Clients updateClient(Clients client) {
        return clientsRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientsRepository.deleteById(id);
    }
}
