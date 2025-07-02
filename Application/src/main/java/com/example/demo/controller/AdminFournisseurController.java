package com.example.demo.controller;

import com.example.demo.entity.Disponibilite;
import com.example.demo.entity.Fournisseurs;
import com.example.demo.entity.Produits;
import com.example.demo.repository.DisponibiliteRepository;
import com.example.demo.service.FournisseurStatService;
import com.example.demo.service.FournisseursService;
import com.example.demo.service.ProduitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AdminFournisseurController {
    @Autowired
    private FournisseursService fournisseursService;
    @Autowired
    private ProduitsService produitsService;
    @Autowired
    private DisponibiliteRepository disponibiliteRepository;
    @Autowired
    private FournisseurStatService fournisseurStatService;

    private static final Logger log = LoggerFactory.getLogger(AdminFournisseurController.class);

    @GetMapping("/admin/fournisseurs/ajout")
    public String showAjoutFournisseurForm(Model model) {
        model.addAttribute("fournisseur", new Fournisseurs());
        return "admin/fournisseurs/ajout";
    }

    @PostMapping("/admin/fournisseurs/ajout")
    public String addFournisseur(@ModelAttribute("fournisseur") Fournisseurs fournisseur, Model model) {
        fournisseursService.save(fournisseur);
        model.addAttribute("success", "Fournisseur ajouté avec succès !");
        model.addAttribute("fournisseur", new Fournisseurs());
        return "admin/fournisseurs/ajout";
    }

    @GetMapping("/admin/fournisseurs/liste")
    public String listeFournisseurs(Model model) {
        List<Fournisseurs> fournisseurs = fournisseursService.findAll();
        Map<Long, List<Produits>> produitsParFournisseur = new HashMap<>();
        for (Fournisseurs f : fournisseurs) {
            List<Produits> produits = fournisseursService.getProduitsByFournisseur(f.getId());
            produitsParFournisseur.put(f.getId(), produits);
        }
        model.addAttribute("fournisseurs", fournisseurs);
        model.addAttribute("produitsParFournisseur", produitsParFournisseur);
        return "admin/fournisseurs/liste";
    }

    @GetMapping("/admin/fournisseurs/edit")
    public String editFournisseurForm(Long id, Model model) {
        Fournisseurs fournisseur = fournisseursService.findById(id);
        model.addAttribute("fournisseur", fournisseur);
        return "admin/fournisseurs/edit";
    }

    @PostMapping("/admin/fournisseurs/update")
    public String updateFournisseur(@ModelAttribute("fournisseur") Fournisseurs fournisseur) {
        fournisseursService.save(fournisseur);
        return "redirect:/admin/fournisseurs/liste";
    }

    @GetMapping("/admin/fournisseurs/delete")
    public String deleteFournisseur(Long id) {
        fournisseursService.deleteById(id);
        return "redirect:/admin/fournisseurs/liste";
    }

    @GetMapping("/admin/fournisseurs/ajout-disponibilite")
    public String showAjoutDisponibiliteForm(Model model) {
        model.addAttribute("disponibilite", new Disponibilite());
        model.addAttribute("fournisseurs", fournisseursService.findAll());
        model.addAttribute("produits", produitsService.getAll());
        return "admin/fournisseurs/ajout-disponibilite";
    }

    @PostMapping("/admin/fournisseurs/ajout-disponibilite")
    public String addDisponibilite(@ModelAttribute("disponibilite") Disponibilite disponibilite, Model model) {
        disponibiliteRepository.save(disponibilite);
        model.addAttribute("success", "Disponibilité ajoutée avec succès !");
        return "redirect:/admin/fournisseurs/ajout-disponibilite";
    }

    @GetMapping("/admin/fournisseurs/statistique")
    public String statistiquesFournisseurs(
            @RequestParam(value = "dateDebut", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(value = "dateFin", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate dateFin,
            Model model) {
        if (dateDebut == null)
            dateDebut = LocalDate.now().minusMonths(1);
        if (dateFin == null)
            dateFin = LocalDate.now();
        long totalFournisseurs = fournisseurStatService.getTotalFournisseurs(dateDebut, dateFin);
        List<Fournisseurs> nouveauxFournisseurs = fournisseurStatService.getNouveauxFournisseurs(dateDebut, dateFin);
        Double moyenneProduits = fournisseurStatService.getMoyenneProduitsParFournisseur(dateDebut, dateFin);
        List<Map<String, Object>> topFournisseurs = fournisseurStatService.getTopFournisseursByVentes(dateDebut,
                dateFin);
        log.info(
                "[CONTROLLER] Statistiques envoyées à la vue : totalFournisseurs={}, nouveauxFournisseurs={}, moyenneProduits={}, topFournisseurs={}",
                totalFournisseurs, nouveauxFournisseurs.size(), moyenneProduits, topFournisseurs.size());
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("totalFournisseurs", totalFournisseurs);
        model.addAttribute("nouveauxFournisseurs", nouveauxFournisseurs);
        model.addAttribute("moyenneProduits", moyenneProduits);
        model.addAttribute("topFournisseurs", topFournisseurs);
        return "admin/fournisseurs/statistique";
    }
}
