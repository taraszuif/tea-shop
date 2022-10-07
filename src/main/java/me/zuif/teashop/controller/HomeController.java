package me.zuif.teashop.controller;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.service.impl.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final TeaService teaService;

    @Autowired
    public HomeController(TeaService teaService) {
        this.teaService = teaService;
    }

    @GetMapping(value = {"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("teas", getAllTeas());
        model.addAttribute("teasCount", teasCount());
        return "home";
    }

    @RequestMapping("/searchByType")
    public String homePost(@RequestParam("teaType") TeaType teaType, Model model) {
        model.addAttribute("teas", teaService.findAllByTeaType(teaType));
        model.addAttribute("teasCount", teaService.count());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    private List<Tea> getAllTeas() {
        return teaService.findAllByOrderByIdAsc();
    }

    private long teasCount() {
        return teaService.count();
    }
}
