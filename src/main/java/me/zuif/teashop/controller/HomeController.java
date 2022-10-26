package me.zuif.teashop.controller;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.service.impl.TeaService;
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
        PageOptions pageOptions = PageOptions.retrieveFromRequest(request);
        boolean sort = false;
        boolean sortBy = false;
        FindOptions findOptions = new FindOptions();
        if (request.getParameter("search") != null && !request.getParameter("search").isEmpty()) {
            String search = request.getParameter("search");
            findOptions.setData("%" + search + "%");
            findOptions.setType(FindOptions.FindType.SEARCH);
        } else if (request.getParameter("teaType") != null && !request.getParameter("teaType").isEmpty()) {
            TeaType type = TeaType.valueOf(request.getParameter("teaType"));
            findOptions.setData(type);
            findOptions.setType(FindOptions.FindType.TYPE);
        } else {
            findOptions.setType(FindOptions.FindType.DEFAULT);
        }
        if (request.getParameter("sort") != null && !request.getParameter("sort").isEmpty()) {
            List<String> sortValues = List.of("count", "price", "addTime");
            if (sortValues.contains(request.getParameter("sort"))) {
                sort = true;
            }
        }
        if (request.getParameter("sortBy") != null && !request.getParameter("sortBy").isEmpty()) {
            sortBy = sort;
        }
        Page<Tea> find = null;
        boolean searchDetail = false;
        PageRequest pageRequest = PageRequest.of(pageOptions.getPage(), pageOptions.getSize());
        if (sort) {
            String sortValue = request.getParameter("sort");
            Sort.Direction direction = sortBy && request.getParameter("sortBy").equals("descend") ? Sort.Direction.DESC : Sort.Direction.ASC;
            switch (findOptions.getType()) {
                case SEARCH -> {
                    String name = (String) findOptions.getData();
                    find = teaService.findAllByNameLike(name, PageRequest.of(pageOptions.getPage(), pageOptions.getSize(), direction, sortValue));
                    if (find.getTotalElements() == 0) {
                        searchDetail = true;
                        find = teaService.findAllByNameLikeOrDescriptionLikeOrManufacturerLike(name, name, name, PageRequest.of(pageOptions.getPage(), pageOptions.getSize(), direction, sortValue));
                    }
                }
                case TYPE -> {
                    TeaType type = (TeaType) findOptions.getData();
                    find = teaService.findAllByTeaType(type, PageRequest.of(pageOptions.getPage(), pageOptions.getSize(), direction, sortValue));
                }
                case DEFAULT ->
                        find = teaService.findAll(PageRequest.of(pageOptions.getPage(), pageOptions.getSize(), direction, sortValue));

            }
        } else {
            switch (findOptions.getType()) {
                case SEARCH -> {
                    String name = (String) findOptions.getData();
                    find = teaService.findAllByNameLike(name, pageRequest);
                    if (find.getTotalElements() == 0) {
                        searchDetail = true;
                        find = teaService.findAllByNameLikeOrDescriptionLikeOrManufacturerLike(name, name, name, PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
                    }
                }
                case TYPE -> {
                    TeaType type = (TeaType) findOptions.getData();
                    find = teaService.findAllByTeaType(type, pageRequest);
                }
                case DEFAULT -> find = teaService.findAll(pageRequest);
            }
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
