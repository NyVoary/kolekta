package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Produits;
import com.example.demo.entity.Fournisseurs;
import com.example.demo.entity.Categories;
import com.example.demo.entity.ProduitsMois;
import com.example.demo.service.ProduitsService;
import com.example.demo.service.FournisseursService;
import com.example.demo.service.CategoriesService;
import com.example.demo.service.ProduitsMoisService;
import com.example.demo.service.StatistiquesService;

import java.time.LocalDate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Controller
public class ProduitsControllerAdmin {
    
    @Autowired
    private ProduitsService produitsService;
    
    @Autowired
    private FournisseursService fournisseursService;
    
    @Autowired
    private CategoriesService categoriesService;
    
    @Autowired
    private ProduitsMoisService produitsMoisService;
    
    @Autowired
    private StatistiquesService statistiquesService;

    @GetMapping("/admin/ajout-produit")
     public String addProduct(Model model) {
        model.addAttribute("fournisseurs", fournisseursService.getAll());
        model.addAttribute("categories", categoriesService.getAll());
        return "admin/produits/ajout-produit";
    }

    @PostMapping("/admin/ajout-produit")
    public String saveProduct(
            @RequestParam("fournisseur") int idFournisseur,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("categorie") int idCategorie,
            @RequestParam("unite_mesure") String uniteMesure,
            @RequestParam("prix_achat") double prixAchat,
            @RequestParam("prix_vente") double prixVente,
            @RequestParam("image") MultipartFile image,
            Model model) {
        
        try {
            Produits produit = new Produits();
            
            Fournisseurs fournisseur = fournisseursService.getById(idFournisseur);
            Categories categorie = categoriesService.getById(idCategorie);
            
            produit.setFournisseur(fournisseur);
            produit.setNom(nom);
            produit.setDescription(description);
            produit.setCategorie(categorie);
            produit.setUniteMesure(uniteMesure);
            produit.setPrixAchat(prixAchat);
            produit.setPrixAchat(prixVente);
            produit.setDateAjout(LocalDate.now());
            
            if (!image.isEmpty()) {
                String imageName = saveImage(image);
                produit.setImage(imageName);
            }
            
            produitsService.saveOrUpdate(produit);
            
            return "redirect:/admin/liste-produit";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'ajout du produit: " + e.getMessage());
            model.addAttribute("fournisseurs", fournisseursService.getAll());
            model.addAttribute("categories", categoriesService.getAll());
            return "admin/produits/ajout-produit";
        }
    }
    
    private String saveImage(MultipartFile image) throws IOException {
        String uploadDir = "src/main/webapp/images/produits/";
        Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath);
        
        return fileName;
    }
    @GetMapping("/admin/liste-produit")
     public String listProduct(Model model) {
        model.addAttribute("produits", produitsService.getAll());
        model.addAttribute("fournisseurs", fournisseursService.getAll());
        model.addAttribute("categories", categoriesService.getAll());
        return "admin/produits/liste-produit";
    }

    @GetMapping("/admin/ajout-produitMois")
    public String addProductMois(Model model) {
        model.addAttribute("produits", produitsService.getAll());
        return "admin/produits/ajout-produitMois";
    }

    @PostMapping("/admin/ajout-produitMois")
    public String saveProductMois(
            @RequestParam("produit") Long idProduit,
            @RequestParam("mois") int mois,
            Model model) {
        
        try {
            ProduitsMois produitMois = new ProduitsMois();
            
            Produits produit = produitsService.getBYid(idProduit);
            produitMois.setProduits(produit);
            produitMois.setMois(mois);
            
            produitsMoisService.saveOrUpdate(produitMois);
            
            return "redirect:/admin/liste-produit";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'ajout du produit mois: " + e.getMessage());
            model.addAttribute("produits", produitsService.getAll());
            return "admin/produits/ajout-produitMois";
        }
    }
    
    @PostMapping("/admin/supprimer-produit")
    public String deleteProduct(@RequestParam("id") Long id) {
        try {
            produitsService.delete(id);
            return "redirect:/admin/liste-produit";
        } catch (Exception e) {
            return "redirect:/admin/liste-produit?error=Erreur lors de la suppression";
        }
    }

    @PostMapping("/admin/modifier-produit")
    public String updateProduct(
            @RequestParam("id") Long id,
            @RequestParam("fournisseur") int idFournisseur,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("categorie") int idCategorie,
            @RequestParam("unite_mesure") String uniteMesure,
            @RequestParam("prix_achat") double prixAchat,
            @RequestParam("prix_vente") double prixVente,
            @RequestParam("image") MultipartFile image,
            Model model) {
        
        try {
            Produits produit = produitsService.getBYid(id);
            
            Fournisseurs fournisseur = fournisseursService.getById(idFournisseur);
            Categories categorie = categoriesService.getById(idCategorie);
            
            produit.setFournisseur(fournisseur);
            produit.setNom(nom);
            produit.setDescription(description);
            produit.setCategorie(categorie);
            produit.setUniteMesure(uniteMesure);
            produit.setPrixAchat(prixAchat);
            produit.setPrixVente(prixVente);
            
            if (!image.isEmpty()) {
                String imageName = saveImage(image);
                produit.setImage(imageName);
            }
            
            produitsService.saveOrUpdate(produit);
            
            return "redirect:/admin/liste-produit";
        } catch (Exception e) {
            return "redirect:/admin/liste-produit?error=Erreur lors de la modification";
        }
    }

    @GetMapping("/admin/stat-produit")
     public String statProduct(Model model) {
        try {
            Map<String, Object> stats = statistiquesService.getToutesLesStatistiques();
            model.addAttribute("statistics", stats);
            return "admin/produits/statistique";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement des statistiques: " + e.getMessage());
            return "admin/produits/statistique";
        }
    }
}

