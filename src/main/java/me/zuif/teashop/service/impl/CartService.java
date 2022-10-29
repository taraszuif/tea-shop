package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.order.Order;
import me.zuif.teashop.model.order.OrderDetails;
import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartService implements ICartService {
    private final Map<String, Integer> cart = new LinkedHashMap<>();
    private final TeaService teaService;

    @Autowired
    public CartService(TeaService teaService) {
        this.teaService = teaService;
    }

    @Override
    public void addTea(String id) {

        if (cart.containsKey(id)) {
            cart.put(id, cart.get(id) + 1);
        } else {
            cart.put(id, 1);
        }

    }

    @Override
    public void removeTea(String id) {
        if (cart.containsKey(id)) {
            if (cart.get(id) > 1)
                cart.replace(id, cart.get(id) - 1);
            else if (cart.get(id) == 1) {
                cart.remove(id);
            }
        }
    }

    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public Map<Tea, Integer> getCart() {
        return Collections.unmodifiableMap(cart.entrySet().stream().collect(Collectors.toMap(e -> teaService.findById(e.getKey()), e -> e.getValue())));
    }

    @Override
    public BigDecimal totalPrice() {
        return cart.entrySet().stream()
                .map(k -> teaService.findById(k.getKey()).getPrice().multiply(BigDecimal.valueOf(k.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


    @Override
    public Optional<Order> checkout() throws CloneNotSupportedException {
        Order result = new Order();
        result.setTotalPrice(totalPrice());
        List<OrderDetails> orderTeas = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Tea tea = teaService.findById(entry.getKey());
            if (tea.getCount() < entry.getValue())
                return Optional.empty();
            OrderDetails teaCount = new OrderDetails();
            teaCount.setTea(tea);
            teaCount.setCount(entry.getValue());
            teaCount.setOrder(result);
            orderTeas.add(teaCount);
            teaService.findById(entry.getKey()).setCount(tea.getCount() - entry.getValue());
        }
        List<Tea> cartSet = cart.keySet().stream().map(s -> teaService.findById(s)).collect(Collectors.toList());
        teaService.saveAll(new ArrayList<>(cartSet));
        teaService.flush();
        result.setTeas(cartSet);
        result.setDetails(orderTeas);
        cart.clear();
        return Optional.of(result);

    }

}
