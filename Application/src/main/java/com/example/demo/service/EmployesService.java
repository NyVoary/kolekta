package com.example.demo.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.EmployesDTO;
import com.example.demo.entity.Employes;
import com.example.demo.repository.EmployesRepository;

@Service
public class EmployesService {
    
    private final EmployesRepository repository;

    @Autowired
    public EmployesService(EmployesRepository repository) {
        this.repository = repository;
    }

    public void CreatOrUpdate(EmployesDTO employesDTO) throws Exception {
        File staticUploads = ResourceUtils.getFile(new File("uploads/").getAbsolutePath());
        if (!staticUploads.exists()) {
            staticUploads.mkdirs();
        }
        MultipartFile image = employesDTO.getImage();
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File destinationFile = new File(staticUploads, fileName);
        image.transferTo(destinationFile);

        if (fileName.endsWith("_")) {
            throw new Exception("L'emploie doit avoir du photo !");
        }

        Employes temp = new Employes();

        temp.setNom(employesDTO.getNom());
        temp.setPrenoms(employesDTO.getPrenoms());
        temp.setDtn(employesDTO.getDtn());
        temp.setSexe(employesDTO.getSexe());
        temp.setEmail(employesDTO.getEmail());
        temp.setTelephone(employesDTO.getTelephone());
        temp.setMdp(employesDTO.getMdp());
        temp.setAdresse(employesDTO.getAdresse());
        temp.setPost(employesDTO.getPost());
        temp.setDate_embauche(employesDTO.getDate_embauche());
        temp.setDate_fin_embauche(employesDTO.getDate_fin_embauche());
        temp.setSalaire(employesDTO.getSalaire());
        temp.setMode_paiement(employesDTO.getMode_paiement());
        temp.setImage(fileName);

        repository.save(temp);
    }

    public List<Employes> getAll() {
        return repository.findAll();
    }

    public Employes GetById(Long id) {
        return repository.findById(id).get();
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }
}
