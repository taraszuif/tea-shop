package me.zuif.teashop.controller;


import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.service.impl.CartService;
import me.zuif.teashop.service.impl.TeaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;
    private final TeaService teaService;

    @Autowired
    public CartController(CartService cartService, TeaService teaService) {
        this.cartService = cartService;
        this.teaService = teaService;
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
            cartService.addTea(tea);
            logger.debug(String.format("Tea with id: %s added to shopping cart.", id));
        }
        return "redirect:/home";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeTeaFromCart(@PathVariable("id") String id) {
        Tea tea = teaService.findById(id);
        logger.debug("remove " + id);
        if (tea != null) {
            cartService.removeTea(tea);
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
    public String cartCheckout() {
        cartService.checkout();

        return "redirect:/cart";
    }
}
