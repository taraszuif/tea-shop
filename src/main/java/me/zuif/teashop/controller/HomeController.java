package me.zuif.teashop.controller;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.tea.TeaType;
import me.zuif.teashop.service.impl.TeaService;
import me.zuif.teashop.utils.ControllerDataObject;
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
        int page = 0;
        int size = 2;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        boolean sort = false;
        boolean sortBy = false;
        ControllerDataObject object = new ControllerDataObject();
        if (request.getParameter("search") != null && !request.getParameter("search").isEmpty()) {
            String search = request.getParameter("search");
            object.setData("%" + search + "%");
            object.setValue("search");
        } else if (request.getParameter("teaType") != null && !request.getParameter("teaType").isEmpty()) {
            TeaType type = TeaType.valueOf(request.getParameter("teaType"));
            object.setData(type);
            object.setValue("teaType");
        } else {
            object.setValue("default");
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
        PageRequest pageRequest = PageRequest.of(page, size);
        if (sort) {
            String sortValue = request.getParameter("sort");
            String sortByValue = request.getParameter("sortBy");
            if (sortBy && sortByValue.equals("descend")) {
                switch (object.getValue()) {
                    case "search" -> {
                        String name = (String) object.getData();
                        find = teaService.findAllByNameLike(name, PageRequest.of(page, size, Sort.Direction.DESC, sortValue));
                        if (find.getTotalElements() == 0) {
                            searchDetail = true;
                            find = teaService.findAllByNameLikeOrDescriptionLikeOrManufacturerLike(name, name, name, PageRequest.of(page, size, Sort.Direction.ASC, sortValue));
                        }
                    }
                    case "teaType" -> {
                        TeaType type = (TeaType) object.getData();
                        find = teaService.findAllByTeaType(type, PageRequest.of(page, size, Sort.Direction.DESC, sortValue));
                    }
                    case "default" ->
                            find = teaService.findAll(PageRequest.of(page, size, Sort.Direction.DESC, sortValue));

                }
            } else {
                switch (object.getValue()) {
                    case "search" -> {
                        String name = (String) object.getData();
                        find = teaService.findAllByNameLike(name, PageRequest.of(page, size, Sort.Direction.ASC, sortValue));
                        if (find.getTotalElements() == 0) {
                            searchDetail = true;
                            find = teaService.findAllByNameLikeOrDescriptionLikeOrManufacturerLike(name, name, name, PageRequest.of(page, size, Sort.Direction.ASC, sortValue));
                        }
                    }
                    case "teaType" -> {
                        TeaType type = (TeaType) object.getData();
                        find = teaService.findAllByTeaType(type, PageRequest.of(page, size, Sort.Direction.ASC, sortValue));
                    }
                    case "default" ->
                            find = teaService.findAll(PageRequest.of(page, size, Sort.Direction.ASC, sortValue));

                }
            }
        } else {
            switch (object.getValue()) {
                case "search" -> {
                    String name = (String) object.getData();
                    find = teaService.findAllByNameLike(name, pageRequest);
                    if (find.getTotalElements() == 0) {
                        searchDetail = true;
                        find = teaService.findAllByNameLikeOrDescriptionLikeOrManufacturerLike(name, name, name, PageRequest.of(page, size));
                    }
                }
                case "teaType" -> {
                    TeaType type = (TeaType) object.getData();
                    find = teaService.findAllByTeaType(type, pageRequest);
                }
                case "default" -> find = teaService.findAll(pageRequest);
            }
        }
        if (find == null) {
            find = teaService.findAll(pageRequest);
        }
        model.addAttribute("searchDetail", searchDetail);
        model.addAttribute("teas", find);
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
