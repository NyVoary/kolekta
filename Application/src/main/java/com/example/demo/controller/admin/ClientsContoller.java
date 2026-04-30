package com.example.demo.controller.admin;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Clients;
import com.example.demo.entity.Commandes;
import com.example.demo.service.ClientService;
import com.example.demo.service.CommandeService;

@Controller
public class ClientsContoller {
    private final ClientService clientService;
    private final CommandeService commandeService;

    public ClientsContoller(ClientService clientService, CommandeService commandeService) {
        this.clientService = clientService;
        this.commandeService = commandeService;
    }

    @GetMapping("/admin/ajout-client")
    public String ajoutClient(Model model) {
        model.addAttribute("client", new Clients());
        return "admin/clients/ajout-client";
    }

    @PostMapping("/admin/ajout-client")
    public String enregistrerClient(@ModelAttribute Clients client,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {
        try {
            LocalDate aujourdHui = LocalDate.now();
            LocalDate dateNaissance = client.getDtn();
            Period age = Period.between(dateNaissance, aujourdHui);

            if (age.getYears() < 18) {
                model.addAttribute("erreur", "Vous devez avoir au moins 18 ans.");
                return "admin/clients/ajout-client";
            }
            ClientDTO dto = new ClientDTO();
            dto.setNom(client.getNom());
            dto.setPrenoms(client.getPrenoms());
            dto.setDtn(client.getDtn());
            dto.setSexe(client.getSexe());
            dto.setEmail(client.getEmail());
            dto.setTelephone(client.getTelephone());
            dto.setAdresse(client.getAdresse());
            dto.setMdp(client.getMdp());
            dto.setImage(imageFile);

            clientService.inscrire(dto);
            return "redirect:/admin/liste-client";
        } catch (Exception e) {
            model.addAttribute("erreur", e.getMessage());
            return "admin/clients/ajout-client"; // Le JSP correspondant au formulaire
        }
    }

    @GetMapping("/admin/liste-client")
    public String listeClient(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "admin/clients/liste";
    }

    @GetMapping("/admin/stat-client")
    public String statClient(Model model) {
        return "admin/clients/statistique";
    }

    @GetMapping("/admin/modifier-client/{id}")
    public String modifierClient(@PathVariable("id") Long id, Model model) {
        Clients client = clientService.getClientByID(id);
        model.addAttribute("client", client);
        return "admin/clients/modifier-client";
    }

    // @PostMapping("/admin/update-client")
    // public String updateClient(@ModelAttribute Clients client,
    // @RequestParam(value = "imageFile", required = false) MultipartFile imageFile)
    // throws Exception {
    // // Clients existingClient = clientService.getClientByID(client.getId());
    // Clients existingClient = clientService.getClientByID(client.getId());

    // if (imageFile != null && !imageFile.isEmpty()) {
    // String fileName = System.currentTimeMillis() + "_" +
    // imageFile.getOriginalFilename();
    // File staticUploads = ResourceUtils
    // .getFile(new
    // File("src/main/resources/static/images/uploads").getAbsolutePath());
    // File destinationFile = new File(staticUploads, fileName);
    // imageFile.transferTo(destinationFile);
    // existingClient.setImage(fileName);
    // }

    // existingClient.setNom(client.getNom());
    // existingClient.setPrenoms(client.getPrenoms());
    // existingClient.setDtn(client.getDtn());
    // existingClient.setSexe(client.getSexe());
    // existingClient.setEmail(client.getEmail());
    // existingClient.setTelephone(client.getTelephone());
    // existingClient.setAdresse(client.getAdresse());

    // if (client.getMdp() != null && !client.getMdp().isEmpty()) {
    // existingClient.setMdp(client.getMdp());
    // }

    // clientService.updateClient(existingClient);
    // return "redirect:/admin/liste-client";
    // }

    @PostMapping("/admin/update-client")
    public String updateClient(
            @RequestParam("idClient") Long idClient,
            @ModelAttribute Clients client,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws Exception {
        Clients existingClient = clientService.getClientByID(idClient);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File staticUploads = ResourceUtils.getFile("src/main/resources/static/images/uploads");
            File destinationFile = new File(staticUploads, fileName);
            imageFile.transferTo(destinationFile);
            existingClient.setImage(fileName);
        }

        existingClient.setNom(client.getNom());
        existingClient.setPrenoms(client.getPrenoms());
        existingClient.setDtn(client.getDtn());
        existingClient.setSexe(client.getSexe());
        existingClient.setEmail(client.getEmail());
        existingClient.setTelephone(client.getTelephone());
        existingClient.setAdresse(client.getAdresse());

        if (client.getMdp() != null && !client.getMdp().isEmpty()) {
            existingClient.setMdp(client.getMdp());
        }

        clientService.updateClient(existingClient);
        return "redirect:/admin/liste-client";
    }

    @GetMapping("/admin/supprimer-client/{id}")
    public String supprimerClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return "redirect:/admin/liste-client";
    }

    @GetMapping("/admin/informations-client/{id}")
    public String informationsClient(@PathVariable("id") Long id, Model model) {
        Clients client = clientService.getClientByID(id);
        if (client == null) {
            return "redirect:/admin/liste-client";
        }

        List<Commandes> commandes = commandeService.getAllCommandesByClientWithProduits(id);

        for (Commandes commande : commandes) {
            String statut = commandeService.getDernierStatutCommande(commande.getId());
            commande.setDernierStatut(statut);
        }

        model.addAttribute("client", client);
        model.addAttribute("commandes", commandes);

        return "admin/clients/informations-client";
    }
}