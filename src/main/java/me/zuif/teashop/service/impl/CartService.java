package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.tea.Tea;
import me.zuif.teashop.service.ICartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartService implements ICartService {
    private final Map<Tea, Integer> cart = new LinkedHashMap<>();

    @Override
    public void addTea(Tea tea) {
        if (cart.containsKey(tea)) {
            cart.replace(tea, cart.get(tea) + 1);
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
                .map(k -> k.getKey().getPrice().multiply(BigDecimal.valueOf(k.getValue()))).sorted()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void checkout() {
        cart.clear();
        // здесь должно быть снятие денег и т.д
    }
}