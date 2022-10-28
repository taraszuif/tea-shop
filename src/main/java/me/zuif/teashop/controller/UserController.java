package me.zuif.teashop.controller;

import me.zuif.teashop.model.user.Role;
import me.zuif.teashop.model.user.User;
import me.zuif.teashop.service.impl.UserService;
import me.zuif.teashop.utils.PageOptions;
import me.zuif.teashop.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.format.DateTimeFormatter;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/user")
    public String userPanel(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            return "error/404";
        }

        return "user";
    }

    @GetMapping("/user/list")
    public String userList(HttpServletRequest request, Model model) {
        PageOptions pageOptions = PageOptions.retrieveFromRequest(request);
        Page<User> users = userService.findAll(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        model.addAttribute("page", users);
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "user-list";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") String id, Model model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("userForm", user);
            model.addAttribute("roles", Role.values());
            model.addAttribute("method", "edit");
            return "user-edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") String id, @ModelAttribute("teaForm") User userForm, BindingResult bindingResult, Model model) {
        User before = userService.findById(userForm.getId());
        if (userForm.getPassword().isEmpty()) {
            userForm.setPassword(before.getPassword());
        }
        if (userForm.getRole() == null) {
            userForm.setRole(before.getRole());
        }
        userForm.setAddTime(before.getAddTime());

        userService.update(id, userForm);
        logger.debug(String.format("User with id: %s has been successfully edited.", id));

        return "redirect:/user/list";
    }


    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        User user = userService.findById(id);
        if (user != null) {
            logger.debug(String.format("Tea with id: %s successfully deleted.", user.getId()));
            return "redirect:/user/list";
        } else {
            return "error/404";
        }
    }
}
