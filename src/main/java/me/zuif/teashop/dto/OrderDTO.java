package me.zuif.teashop.dto;

import lombok.Data;
import me.zuif.teashop.model.order.Order;
import me.zuif.teashop.model.order.OrderDetails;

import java.util.Map;

@Data
public class OrderDTO {
    private Order order;
    private Map<String, OrderDetails> detailsMap;
}
