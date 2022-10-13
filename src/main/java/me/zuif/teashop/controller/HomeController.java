package me.zuif.teashop.controller;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.service.impl.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        if (request.getParameter("search") != null && !request.getParameter("search").isEmpty()) {
            String search = request.getParameter("search");
            Page<Tea> find = teaService.findAllByNameLike("%" + search + "%", PageRequest.of(page, size));
            model.addAttribute("teas", find);
            model.addAttribute("teasCount", find.getTotalElements());
        } else if (request.getParameter("teaType") != null && !request.getParameter("teaType").isEmpty()) {
            TeaType type = TeaType.valueOf(request.getParameter("teaType"));
            Page<Tea> find = teaService.findAllByTeaType(type, PageRequest.of(page, size));
            model.addAttribute("teas", find);
            model.addAttribute("teasCount", find.getTotalElements());
        } else {
            model.addAttribute("teas", teaService.findAll(PageRequest.of(page, size)));
            model.addAttribute("teasCount", teasCount());
        }
        model.addAttribute("search", "");

        model.addAttribute("types", TeaType.values());
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
