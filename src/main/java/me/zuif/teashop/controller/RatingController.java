package me.zuif.teashop.controller;

import me.zuif.teashop.model.Rating;
import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.user.User;
import me.zuif.teashop.service.IRatingService;
import me.zuif.teashop.service.ITeaService;
import me.zuif.teashop.service.IUserService;
import me.zuif.teashop.validator.RatingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class RatingController {
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);
    private final IRatingService ratingService;
    private final IUserService userService;
    private final ITeaService teaService;
    private final RatingValidator ratingValidator;

    @Autowired
    public RatingController(IRatingService ratingService, @Qualifier("userServiceImpl") IUserService userService, ITeaService teaService, RatingValidator ratingValidator) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.teaService = teaService;
        this.ratingValidator = ratingValidator;
    }

    @GetMapping("/rating/new/{id}")
    public String newRating(Model model) {
        model.addAttribute("ratingForm", new Rating());
        model.addAttribute("method", "new");
        return "rating";
    }

    @PostMapping("/rating/new/{id}")
    public String newRating(@PathVariable("id") String id, @ModelAttribute("ratingForm") Rating ratingForm, BindingResult bindingResult, Model model) {
        ratingValidator.validate(ratingForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "new");
            return "rating";
        }

        ratingForm.setAddTime(LocalDateTime.now());
        Tea tea = teaService.findById(id);
        if (tea == null) {
            return "redirect:/home";
        }
        ratingForm.setTea(tea);

        String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByUsername(userName);
        ratingForm.setUser(user);
        ratingService.save(ratingForm);
        logger.debug(String.format("Rating with id: %s created.", ratingForm.getId()));

        return "redirect:/tea/about/" + tea.getId();
    }

    @GetMapping("/rating/edit/{id}")
    public String editRating(@PathVariable("id") String id, Model model) {
        Optional<Rating> rating = ratingService.findById(id);
        if (rating.isPresent()) {
            model.addAttribute("ratingForm", rating.get());
            model.addAttribute("method", "edit");
            return "rating";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/rating/edit/{id}")
    public String editRating(@PathVariable("id") String id, @ModelAttribute("ratingForm") Rating ratingForm, BindingResult bindingResult, Model model) {
        ratingValidator.validate(ratingForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "edit");
            return "rating";
        }
        String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Rating rating = ratingService.findById(id).get();
        if (!userName.equals(rating.getUser().getUserName())) {
            logger.error("Security error: " + userName + " trying to edit foreign rating");
            model.addAttribute("method", "edit");
            return "rating";
        }
        ratingForm.setAddTime(rating.getAddTime());
        ratingForm.setUser(rating.getUser());
        ratingForm.setTea(rating.getTea());
        ratingService.update(id, ratingForm);
        logger.debug(String.format("Rating with id: %s has been successfully edited.", id));

        return "redirect:/tea/about/" + rating.getTea().getId();
    }


    @PostMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") String id) {
        Optional<Rating> ratingOptional = ratingService.findById(id);
        if (ratingOptional.isPresent()) {
            String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            Rating rating = ratingOptional.get();
            if (!userName.equals(rating.getUser().getUserName())) {
                logger.error("Security error: " + userName + " trying to delete foreign rating");
                return "redirect:/tea/about/" + rating.getTea().getId();
            }
            ratingService.delete(id);
            logger.debug(String.format("Rating with id: %s successfully deleted.", rating.getId()));
            return "redirect:/tea/about/" + rating.getTea().getId();
        } else {
            return "error/404";
        }
    }
}
