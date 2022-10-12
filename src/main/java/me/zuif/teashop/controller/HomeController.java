package me.zuif.teashop.controller;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.service.impl.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {
    private final TeaService teaService;

    @Autowired
    public HomeController(TeaService teaService) {
        this.teaService = teaService;
    }

    @GetMapping(value = {"/", "/index", "/home"})
    public String home(HttpServletRequest request, Model model) {
        int page = 0;
        int size = 2;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("teas", teaService.findAll(PageRequest.of(page, size)));
        model.addAttribute("teasCount", teasCount());
        model.addAttribute("types", TeaType.values());
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
