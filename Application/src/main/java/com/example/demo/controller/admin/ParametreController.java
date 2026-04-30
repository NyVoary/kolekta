package com.example.demo.controller.admin;

import com.example.demo.entity.Parametre;
import com.example.demo.repository.ParametreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parametres")
public class ParametreController {

    @Autowired
    private ParametreRepository repo;

    @GetMapping
    public String lister(Model model) {
        model.addAttribute("parametres", repo.findAll());
        return "admin/liste";
    }

    @GetMapping("/ajout")
    public String ajout(Model model) {
        model.addAttribute("parametre", new Parametre());
        return "admin/formulaire";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Parametre parametre) {
        repo.save(parametre);
        return "redirect:/parametres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Parametre p = repo.findById(id).orElseThrow();
        model.addAttribute("parametre", p);
        return "admin/formulaire";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repo.deleteById(id);
        return "redirect:/parametres";
    }
}
