package me.zuif.teashop.controller;


import me.zuif.teashop.model.order.Order;
import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.model.user.User;
import me.zuif.teashop.service.ICartService;
import me.zuif.teashop.service.IOrderService;
import me.zuif.teashop.service.ITeaService;
import me.zuif.teashop.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ICartService cartService;
    private final ITeaService teaService;
    private final IOrderService orderService;
    private final IUserService userService;

    public CartController(ICartService cartService, ITeaService teaService, IOrderService orderService, @Qualifier("userServiceImpl") IUserService userService) {
        this.cartService = cartService;
        this.teaService = teaService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("teas", cartService.getCart());
        model.addAttribute("totalPrice", cartService.totalPrice());
        return "cart";
    }

    @GetMapping("/cart/add/{id}")
    public String addTeaToCart(@PathVariable("id") String id) {
        Tea tea = teaService.findById(id);
        if (tea != null) {
            cartService.addTea(id);
            logger.debug(String.format("Tea with id: %s added to shopping cart.", id));
        }
        return "redirect:/home";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeTeaFromCart(@PathVariable("id") String id) {
        Tea tea = teaService.findById(id);
        if (tea != null) {
            cartService.removeTea(id);
            logger.debug(String.format("Tea with id: %s removed from shopping cart.", id));
        } else {

        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearTeasInCart() {
        cartService.clear();
        return "redirect:/cart";
    }

    @GetMapping("/cart/checkout")
    public String cartCheckout(Model model) {
        Optional<Order> orderOptional;

        orderOptional = cartService.checkout();

        if (orderOptional.isEmpty()) {
            model.addAttribute("noTeas", true);
            model.addAttribute("teas", cartService.getCart());
            model.addAttribute("totalPrice", cartService.totalPrice());
            return "cart";
        }
        Order order = orderOptional.get();
        order.setAddTime(LocalDateTime.now());
        String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByUsername(userName);
        order.setUser(user);
        orderService.save(order);
        return "redirect:/cart";
    }
}
