package com.example.demo.service;

import com.example.demo.dto.AccesDTO;
import com.example.demo.entity.Acces;
import com.example.demo.entity.Employes;
import com.example.demo.repository.AccesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccesService {

    private AccesRepository accesRepository;
    private EmployesService employesService;

    public AccesService(AccesRepository accesRepository,
                        EmployesService employesService) {
        this.accesRepository = accesRepository;
        this.employesService = employesService;
        // AccesRepository accesRepository
        // EmployesService employesService
    }

    public String verifierIdentifiants(String username, String password) {
        return accesRepository.findByNomUtilisateur(username)
                .filter(acces -> acces.getMotDePasse().equals(password))
                .map(Acces::getRole)
                .orElse(null); // ou tu peux retourner "invalide" si tu préfères
    }

    public Acces getAccesByUsername(String username) {
        return accesRepository.findByNomUtilisateur(username)
                .orElse(null); // ou tu peux lancer une exception si l'utilisateur n'existe pas
    }

    public void addOrUpdate(AccesDTO accesDTO) {
        Acces temp = new Acces();
        temp.setNomUtilisateur(accesDTO.getNom());
        temp.setMotDePasse(accesDTO.getMdp());
        temp.setRole(accesDTO.getRole());
        temp.setIdAcces(accesDTO.getId());

        if (employesService.GetById(accesDTO.getIdEmp()) != null) {
            Employes empTemp = employesService.GetById(accesDTO.getIdEmp());
            temp.setEmployes(empTemp);
        }

        addOrUpdate(temp);
    }

    public List<Acces> getAll() {
        return  accesRepository.findAll();
    }

    public void addOrUpdate(Acces acces) {
        accesRepository.save(acces);
    }

    public Acces getByid(long id) {
        return accesRepository.findById(id).get();
    }

    public void removeByid(long id) {
        accesRepository.deleteById(id);
    }
}
