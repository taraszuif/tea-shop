package me.zuif.teashop.controller;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.service.impl.TeaService;
import me.zuif.teashop.validator.TeaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeaController {
    private static final Logger logger = LoggerFactory.getLogger(TeaController.class);
    private final TeaService teaService;
    private final TeaValidator teaValidator;

    @Autowired
    public TeaController(TeaService teaService, TeaValidator teaValidator) {
        this.teaService = teaService;

        this.teaValidator = teaValidator;
    }

    @GetMapping("/tea/new")
    public String newTea(Model model) {
        model.addAttribute("teaForm", new Tea());
        model.addAttribute("method", "new");
        return "tea";
    }

    @PostMapping("/tea/new")
    public String newTea(@ModelAttribute("teaForm") Tea teaForm, BindingResult bindingResult, Model model) {
        teaValidator.validate(teaForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "new");
            return "tea";
        }
        teaService.save(teaForm);
        logger.debug(String.format("Tea with id: %s successfully created.", teaForm.getId()));

        return "redirect:/home";
    }

    @GetMapping("/tea/edit/{id}")
    public String editTea(@PathVariable("id") String id, Model model) {
        Tea tea = teaService.findById(id);
        if (tea != null) {
            model.addAttribute("teaForm", tea);
            model.addAttribute("method", "update");
            return "tea";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/tea/edit/{id}")
    public String editTea(@PathVariable("id") String id, @ModelAttribute("teaForm") Tea teaForm, BindingResult bindingResult, Model model) {
        teaValidator.validate(teaForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "update");
            return "tea";
        }
        teaService.update(id, teaForm);
        logger.debug(String.format("Tea with id: %s has been successfully edited.", id));

        return "redirect:/home";
    }

    @PostMapping("/tea/delete/{id}")
    public String deleteTea(@PathVariable("id") String id) {
        Tea tea = teaService.findById(id);
        if (tea != null) {
            teaService.delete(id);
            logger.debug(String.format("Tea with id: %s successfully deleted.", tea.getId()));
            return "redirect:/home";
        } else {
            return "error/404";
        }
    }
}
