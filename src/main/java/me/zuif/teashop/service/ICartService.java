package me.zuif.teashop.service;

import me.zuif.teashop.model.order.Order;
import me.zuif.teashop.model.tea.Tea;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface ICartService {
    void addTea(String id);

    void removeTea(String id);

    void clear();

    Map<Tea, Integer> getCart();

    BigDecimal totalPrice();

    Optional<Order> checkout();
}
