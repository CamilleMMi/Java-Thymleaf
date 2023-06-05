package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.repository.SalarieAideADomicileRepository;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Autowired
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    @GetMapping(value = "/")
    public String home(ModelMap model) {
        model.put("salarieCount", salarieAideADomicileService.countSalaries());
        return "home";
    }

    @GetMapping(value = "/salaries/{id}")
    public String salarie(ModelMap model, @PathVariable Long id) {
        model.put("salarieCount", salarieAideADomicileService.countSalaries());
        model.put("salarie", salarieAideADomicileService.getSalarie(id));
        return "detail_Salarie";
    }

    @PostMapping(value = "/salaries/save")
    public String createSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/";
    }

    @PostMapping(value = "/salaries/{id}")
    public String updateSalarie(SalarieAideADomicile salarie) throws SalarieException {
        try {
            salarieAideADomicileService.updateSalarieAideADomicile(salarie);
            return "redirect:/salaries/" + salarie.getId();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun salarié" + salarie.getId() + "trouvé");
        }
    }

    @GetMapping(value = "/salaries")
    public String getSalaries(ModelMap model) {
        model.put("salarieCount", salarieAideADomicileService.countSalaries());
        model.put("salaries", salarieAideADomicileService.getSalaries());
        return "list";
    }

    @GetMapping(value = "/deleteSalarie")
    public String deleteSalarie(@RequestParam Long id) {
        try {
            salarieAideADomicileRepository.deleteById(id);
            return "redirect:/salaries";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun salarié " + id + "trouvé");
        }
    }

    @RequestMapping(value = "/salarie/findSalarieByName")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView findSalarieByName(@RequestParam String name) {
        ModelAndView salarie = new ModelAndView("list");
        try {
            List<SalarieAideADomicile> salarieList = salarieAideADomicileService.getSalaries(name);
            salarie.addObject("salaries", salarieList);
            salarie.addObject("salarieCount", salarieAideADomicileService.countSalaries());
            return salarie;
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun salarié " + name + "trouvé");
        }
    }

    @RequestMapping(value = {"/salaries/aide/new"})
    public ModelAndView addSalarie(){
        ModelAndView salarie = new ModelAndView("add_Salarie");
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
        salarie.addObject("salarieCount", salarieAideADomicileService.countSalaries());
        salarie.addObject("salarie",salarieAideADomicile);

        return salarie;
    }
}
