package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.repository.SalarieAideADomicileRepository;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Autowired
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    @GetMapping(value = "/deleteSalarie")
    public String deleteSalarie(@RequestParam Long id) throws SalarieException {
        salarieAideADomicileRepository.deleteById(id);
        return "redirect:/salaries";
    }

    @RequestMapping(value = "/salarie/findSalarieByName")
    public ModelAndView findSalarieByName(@RequestParam String name){
        ModelAndView salarie = new ModelAndView("list");
        List<SalarieAideADomicile> salarieList = salarieAideADomicileService.getSalaries(name);
        salarie.addObject("salaries",salarieList);
        return salarie;
    }

    @GetMapping(value = "/")
    public String home(ModelMap model) {
        model.put("salarieCount", salarieAideADomicileService.countSalaries());
        return "home";
    }

    @GetMapping(value = "/salaries/{id}")
    public String salarie(ModelMap model, @PathVariable Long id) {
        model.put("salarie", salarieAideADomicileService.getSalarie(id));
        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries/aide/new")
    public String newSalarie(ModelMap model) {
        return "detail_Salarie";
    }

    @PostMapping(value = "/salaries/save")
    public String createSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @PostMapping(value = "/salaries/{id}")
    public String updateSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.updateSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping(value = "/salaries")
    public String getSalaries(ModelMap model) {
        model.put("salaries", salarieAideADomicileService.getSalaries());
        return "list";
    }
}
