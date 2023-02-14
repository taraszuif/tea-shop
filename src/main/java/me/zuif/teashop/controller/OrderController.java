package me.zuif.teashop.controller;

import me.zuif.teashop.dto.OrderDTO;
import me.zuif.teashop.model.order.Order;
import me.zuif.teashop.model.user.Role;
import me.zuif.teashop.model.user.User;
import me.zuif.teashop.service.IOrderService;
import me.zuif.teashop.service.IUserService;
import me.zuif.teashop.utils.PageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final IUserService userService;
    private final IOrderService orderService;


    @Autowired
    public OrderController(@Qualifier("userServiceImpl") IUserService userService, IOrderService orderService) {


        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/order/about/{id}")
    public String aboutOrder(@PathVariable("id") String id, Model model) {
        Optional<OrderDTO> dtoOptional = orderService.getDTO(id);
        if (dtoOptional.isPresent()) {
            model.addAttribute("order", dtoOptional.get());
            model.addAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return "order-about";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/order/list")
    public String orderList(HttpServletRequest request, Model model) {
        PageOptions pageOptions = PageOptions.retrieveFromRequest(request);
        String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByUsername(userName);
        Page<Order> orders;
        if (user.getRole() == Role.ADMIN) {
            orders = orderService.findAll(PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        } else {
            orders = orderService.findAllByUserId(user.getId(), PageRequest.of(pageOptions.getPage(), pageOptions.getSize()));
        }
        model.addAttribute("page", orders);
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return "order-list";
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") String id) {
        Optional<Order> orderOptional = orderService.findById(id);
        if (orderOptional.isPresent()) {
            orderService.delete(id);
            logger.debug(String.format("Order with id: %s successfully deleted.", id));
            return "redirect:/order/list";
        } else {
            return "error/404";
        }
    }
}
