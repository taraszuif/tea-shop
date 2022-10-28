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

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartService implements ICartService {
    private final Map<Tea, Integer> cart = new LinkedHashMap<>();
    private final TeaService teaService;

    @Autowired
    public CartService(TeaService teaService) {
        this.teaService = teaService;
    }

    @Override
    public void addTea(Tea tea) {
        if (cart.containsKey(tea)) {
            cart.put(tea, cart.get(tea) + 1);
        } else {
            cart.put(tea, 1);
        }

    }

    @Override
    public void removeTea(Tea tea) {
        if (cart.containsKey(tea)) {
            if (cart.get(tea) > 1)
                cart.replace(tea, cart.get(tea) - 1);
            else if (cart.get(tea) == 1) {
                cart.remove(tea);
            }
        }
    }

    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public Map<Tea, Integer> getCart() {
        return Collections.unmodifiableMap(cart);
    }

    @Override
    public BigDecimal totalPrice() {
        return cart.entrySet().stream()
                .map(k -> k.getKey().getPrice().multiply(BigDecimal.valueOf(k.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


    @Override
    public Optional<Order> checkout() throws CloneNotSupportedException {
        Order result = new Order();
        result.setTotalPrice(totalPrice());
        List<OrderDetails> orderTeas = new ArrayList<>();
        for (Map.Entry<Tea, Integer> entry : cart.entrySet()) {
            Tea tea = teaService.findById(entry.getKey().getId());
            if (tea.getCount() < entry.getValue())
                return Optional.empty();
            OrderDetails teaCount = new OrderDetails();
            teaCount.setTea(tea);
            teaCount.setCount(entry.getValue());
            teaCount.setOrder(result);
            orderTeas.add(teaCount);
            entry.getKey().setCount(tea.getCount() - entry.getValue());
        }
        teaService.saveAll(new ArrayList<>(cart.keySet()));
        teaService.flush();
        result.setTeas(cart.keySet().stream().toList());
        result.setDetails(orderTeas);
        cart.clear();
        return Optional.of(result);

    }

}
