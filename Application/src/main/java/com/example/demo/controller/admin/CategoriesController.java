package com.example.demo.controller.admin;

import java.util.List;

import com.example.demo.entity.Categories;
import com.example.demo.service.CategoriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoriesController {
    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/admin/ajout-categorie")
    public String ajoutCategorie(Model model) {
        model.addAttribute("category", new Categories());
        return "admin/categories/ajout-categorie";
    }

     @PostMapping("/admin/ajout-categorie")
    public String addCategory(@ModelAttribute("category") Categories category) {
        categoriesService.saveCategory(category);
        return "redirect:/admin/liste-categorie";
    }

    @GetMapping("/admin/liste-categorie")
    public String listCategories(Model model) {
        List<Categories> categories = categoriesService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/categories/liste";
    }

    @GetMapping("/admin/modifier-categorie")
    public String showEditForm(@RequestParam Long id, Model model) {
        Categories categorie = categoriesService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie invalide: " + id));
        model.addAttribute("categorie", categorie);
        return "admin/categories/modifier-categorie";
    }

    @PostMapping("/admin/modifier-categorie")
    public String updateCategory(@RequestParam Long id, 
                               @RequestParam String libelle) {
        Categories categorie = new Categories();
        categorie.setId(id);
        categorie.setLibelle(libelle);
        categoriesService.saveCategory(categorie);
        return "redirect:/admin/liste-categorie";
    }

    @PostMapping("/admin/supprimer-categorie")
    public String deleteCategory(@RequestParam Long id) {
        categoriesService.deleteCategory(id);
        return "redirect:/admin/liste-categorie";
    }
}