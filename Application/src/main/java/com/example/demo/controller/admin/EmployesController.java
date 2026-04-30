package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.EmployesDTO;
import com.example.demo.dto.AccesDTO;
import com.example.demo.dto.AssignationTacheDTO;
import com.example.demo.entity.Acces;
import com.example.demo.entity.AssignationTache;
import com.example.demo.entity.Employes;
import com.example.demo.service.AccesService;
import com.example.demo.service.EmployesService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class EmployesController {

    private final EmployesService service;
    private final AccesService accesService;

    public EmployesController(EmployesService service,
                            AccesService accesService    
    ) {
        this.service = service;
        this.accesService = accesService;
    }

    @GetMapping("/ajout-employe")
    public String formulaire(HttpServletRequest request) {
        request.setAttribute("payments", Employes.Mode_paiement.values());
        request.setAttribute("sexes", Employes.Sexe.values());
        return "admin/employes/ajout";
    }
    
    @PostMapping("/ajout-employe")
    public String ajoutEmploye(@ModelAttribute EmployesDTO employesDTO, RedirectAttributes redirectAttributes) {
        try {
            service.Creat(employesDTO);
            redirectAttributes.addFlashAttribute("message", "Employes enregistré avec succes !");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/ajout-employe";
    }

    @GetMapping("/employes/remove/{id}")
    public String removeEmploye(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            service.remove(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/liste-employe";
    }

    @GetMapping("/employes/update/{id}")
    public String updateEmploye(@PathVariable("id") long id, HttpServletRequest request) {
        Employes employe = service.GetById(id);
        request.setAttribute("employe", employe);
        return "forward:/admin/ajout-employe";
    }

    @PostMapping("/employes/update/{id}")
    public String updateEmploye(@PathVariable("id") long id, @ModelAttribute EmployesDTO employesDTO, RedirectAttributes redirectAttributes) {
        try {
            service.update(employesDTO);
            redirectAttributes.addFlashAttribute("message", "Employes modifié avec succes !");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/employes/update/" + id;
    }

    @GetMapping("/liste-employe")
    public String listeEmploye(Model model) {
        model.addAttribute("employes", service.getAll());
        return "admin/employes/liste";
    }

    @GetMapping("/divers-employe")
    public String diversEmploye(Model model) {
        model.addAttribute("employes", service.getAll());
        model.addAttribute("roles", Acces.theRoles);
        return "admin/employes/divers";
    }

    @PostMapping("/divers-employe")
    public String diversEmploye(@ModelAttribute AccesDTO accesDTO, RedirectAttributes redirectAttributes) {
        try {
            accesService.addOrUpdate(accesDTO);
            redirectAttributes.addFlashAttribute("message", "Action réussi");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/divers-employe";
    }

    @GetMapping("/list-acces")
    public String listDivers(Model model) {
        model.addAttribute("accs", accesService.getAll());
        return "admin/employes/liste_divers";
    }

    @GetMapping("/divers/remove/{id}")
    public String removeAcces(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            accesService.removeByid(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/list-acces";
    }

    @GetMapping("/divers/update/{id}")
    public String updateAcces(@PathVariable("id") long id, HttpServletRequest request) {
        Acces acc = accesService.getByid(id);
        request.setAttribute("acc", acc);
        return "forward:/admin/divers-employe";
    }

    @PostMapping("/divers/update/{id}")
    public String updateAcces(@PathVariable("id") long id, @ModelAttribute AccesDTO accesDto, RedirectAttributes redirectAttributes) {
        try {
            accesService.addOrUpdate(accesDto);
            // service.update(employesDTO);
            redirectAttributes.addFlashAttribute("message", "Acces modifié avec succes !");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/divers/update/" + id;
    }

    @GetMapping("/tache-employe")
    public String tacheEmploye(Model model) {
        model.addAttribute("status", AssignationTache.theStatus);
        model.addAttribute("priorites", AssignationTache.Priorite.values());
        model.addAttribute("employes", service.getAll());
        return "admin/employes/tache";
    }

    @PostMapping("/tache-employe")
    public String tache(@ModelAttribute AssignationTacheDTO tacheDTO ,RedirectAttributes redirectAttributes) {
        try {
            service.addTache(tacheDTO);
            redirectAttributes.addFlashAttribute("message", "Ajout d'un tache réussi");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/admin/tache-employe";
    }

    @GetMapping("/stat-employe")
    public String statEmploye(Model model) {
        return "admin/employes/statistique";
    }
}
