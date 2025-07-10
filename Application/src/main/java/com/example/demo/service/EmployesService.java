package com.example.demo.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.AssignationTacheDTO;
import com.example.demo.dto.EmployesDTO;
import com.example.demo.entity.AssignationTache;
import com.example.demo.entity.Employes;
import com.example.demo.repository.EmployesRepository;

@Service
public class EmployesService {

    private final EmployesRepository repository;
    private final AssignationTacheService assignationTacheService;

    public EmployesService(EmployesRepository repository,
                            AssignationTacheService assignationTacheService
    ) {
        this.repository = repository;
        this.assignationTacheService = assignationTacheService;
    }

    public Optional<Employes> findById(Long id) {
        return repository.findById(id);
    }

    public void update(EmployesDTO employesDTO) throws Exception {
        Employes existing = repository.findById(employesDTO.getId_employe())
                .orElseThrow(() -> new Exception("Employé introuvable"));

        MultipartFile image = employesDTO.getImage();
        String imageName = existing.getImage();
        File uploadDir;

        if (image != null && !image.isEmpty() && !image.getOriginalFilename().isBlank()) {
            imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            uploadDir = ResourceUtils.getFile(new File("uploads/").getAbsolutePath());

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File destinationFile = new File(uploadDir, imageName);
            image.transferTo(destinationFile);

            imageName = "uploads/" + imageName;
        }

        existing.setNom(employesDTO.getNom());
        existing.setPrenoms(employesDTO.getPrenoms());
        existing.setDtn(employesDTO.getDtn());
        existing.setSexe(employesDTO.getSexe());
        existing.setEmail(employesDTO.getEmail());
        existing.setTelephone(employesDTO.getTelephone());
        existing.setMdp(employesDTO.getMdp());
        existing.setAdresse(employesDTO.getAdresse());
        existing.setPost(employesDTO.getPost());
        existing.setDate_embauche(employesDTO.getDate_embauche());
        existing.setDate_fin_embauche(employesDTO.getDate_fin_embauche());
        existing.setSalaire(employesDTO.getSalaire());
        existing.setMode_paiement(employesDTO.getMode_paiement());
        existing.setImage(imageName);

        repository.save(existing);
    }

    public void Creat(EmployesDTO employesDTO) throws Exception {
        MultipartFile image = employesDTO.getImage();
        String imageName;
        File uploadDir;

        if (image == null || image.isEmpty() || image.getOriginalFilename().isBlank()) {
            imageName = "images/default_emp.jpg";
        } else {
            imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            uploadDir = ResourceUtils.getFile(new File("uploads/").getAbsolutePath());

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File destinationFile = new File(uploadDir, imageName);
            image.transferTo(destinationFile);

            imageName = "uploads/" + imageName;
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
        temp.setImage(imageName);

        repository.save(temp);
    }

    public List<Employes> getAll() {
        return repository.findAll();
    }

    public void addTache(AssignationTacheDTO assignationTacheDTO) {
        AssignationTache temp = new AssignationTache();
        temp.setTitre(assignationTacheDTO.getTitre());
        temp.setDescription(assignationTacheDTO.getDescription());
        temp.setEmployes(GetById(assignationTacheDTO.getId_employe()));
        temp.setPriorite(assignationTacheDTO.getPriorite());
        temp.setStatus(assignationTacheDTO.getStatus());
        temp.setDate_debut(assignationTacheDTO.getDate_debut());
        temp.setDate_fin(assignationTacheDTO.getDate_fin());

        assignationTacheService.add(temp);
    }

    public Employes GetById(Long id) {
        try {
            Employes temp = repository.findById(id).get();
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }
}
