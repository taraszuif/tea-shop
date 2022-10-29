package me.zuif.teashop.service;

import me.zuif.teashop.model.order.OrderDetails;

public interface IOrderDetailsService {
    OrderDetails findByTeaIdAndOrderId(String teaId, String orderId);
}
