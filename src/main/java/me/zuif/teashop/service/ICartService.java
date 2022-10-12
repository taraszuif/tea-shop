package me.zuif.teashop.service;

import me.zuif.teashop.model.tea.Tea;

import java.math.BigDecimal;
import java.util.Map;

public interface ICartService {
    void addTea(Tea tea);

    void removeTea(Tea tea);

    void clear();

    Map<Tea, Integer> getCart();

    BigDecimal totalPrice();

    boolean checkout();
}
