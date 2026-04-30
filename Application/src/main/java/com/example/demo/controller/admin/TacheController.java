package com.example.demo.controller.admin;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.AssignationsTaches;
import com.example.demo.entity.Employe;
import com.example.demo.entity.HistoriqueStatusTaches;
import com.example.demo.entity.StatusTaches;
import com.example.demo.service.AssignationTacheService;
import com.example.demo.service.EmployeService;
import com.example.demo.service.HistoriqueStatusService;
import com.example.demo.service.StatusTachesService;

@Controller
@RequestMapping("/admin")
public class TacheController {

    private final AssignationTacheService assignationTacheService;
    private final HistoriqueStatusService historiqueStatusService;
    private final EmployeService employeService;
    private final StatusTachesService statusTachesService;

    @Autowired
    public TacheController(AssignationTacheService assignationTacheService,
                         HistoriqueStatusService historiqueStatusService,
                         EmployeService employeService,
                         StatusTachesService statusTachesService) {
        this.assignationTacheService = assignationTacheService;
        this.historiqueStatusService = historiqueStatusService;
        this.employeService = employeService;
        this.statusTachesService = statusTachesService;
    }

    @PostMapping("/changer-statut")
    public String changerStatutTache(
        @RequestParam("tacheId") Long tacheId,
        @RequestParam("nouveauStatutId") Long nouveauStatutId,
        @RequestParam("ancienStatusId") Long ancienStatusId,
        @RequestParam("modifieParId") Long modifieParId,
        RedirectAttributes redirectAttributes) {
        try {
            // 1. Récupérer les entités nécessaires
            AssignationsTaches tache = assignationTacheService.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
            
            StatusTaches nouveauStatut = statusTachesService.getStatusById(nouveauStatutId);
            StatusTaches ancienStatus = statusTachesService.getStatusById(ancienStatusId);
            Employe modifiePar = employeService.findById(modifieParId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
                
            tache.setStatus(nouveauStatut);
            assignationTacheService.save(tache);

            HistoriqueStatusTaches historique = new HistoriqueStatusTaches();
            historique.setTache(tache);
            historique.setModifiePar(modifiePar);
            historique.setStatusTache(ancienStatus);
            historique.setDate(LocalDateTime.now());
            
            historiqueStatusService.save(historique);
            
            redirectAttributes.addFlashAttribute("success", "Statut de la tâche mis à jour avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du statut: " + e.getMessage());
        }
        
        return "redirect:/admin/dashboard";
    }
}