package me.zuif.teashop.controller;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.service.ITeaService;
import me.zuif.teashop.utils.FindOptions;
import me.zuif.teashop.utils.PageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    private final ITeaService teaService;

    @Autowired
    public HomeController(ITeaService teaService) {
        this.teaService = teaService;
    }


    @GetMapping(value = {"/", "/index", "/home"})
    public String home(HttpServletRequest request, Model model) {
        PageOptions pageOptions = PageOptions.retrieveFromRequest(request);
        FindOptions findOptions = FindOptions.retrieveFromRequest(request);

        Page<Tea> find = null;
        boolean searchDetail = false;
        PageRequest pageRequest;
        if (findOptions.isSort()) {
            String sortValue = request.getParameter("sort");
            Sort.Direction direction = findOptions.isSortBy() && request.getParameter("sortBy").equals("descend") ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageRequest = PageRequest.of(pageOptions.getPage(), pageOptions.getSize(), direction, sortValue);
        } else {
            pageRequest = PageRequest.of(pageOptions.getPage(), pageOptions.getSize());
        }
        switch (findOptions.getType()) {
            case SEARCH -> {
                String name = (String) findOptions.getData();
                find = teaService.findAllByNameLike(name, pageRequest);
                if (find.getTotalElements() == 0) {
                    searchDetail = true;
                    find = teaService.findAllByDescriptionLikeOrManufacturerLike(name, name, pageRequest);
                }
            }
            case TYPE -> {
                TeaType type = (TeaType) findOptions.getData();
                find = teaService.findAllByTeaType(type, pageRequest);
            }
            case DEFAULT -> find = teaService.findAll(pageRequest);
        }
        if (find == null) {
            find = teaService.findAll(pageRequest);
        }

        model.addAttribute("searchDetail", searchDetail);
        model.addAttribute("page", find);
        model.addAttribute("teasCount", find.getTotalElements());
        model.addAttribute("search", "");
        model.addAttribute("types", TeaType.values());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


}
