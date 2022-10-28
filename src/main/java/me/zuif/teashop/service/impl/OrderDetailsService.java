package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.order.OrderDetails;
import me.zuif.teashop.repository.OrderDetailsRepository;
import me.zuif.teashop.service.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService implements IOrderDetailsService {
    private final OrderDetailsRepository repository;


    @Autowired
    public OrderDetailsService(OrderDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderDetails findByTeaIdAndOrderId(String teaId, String orderId) {
        return repository.findByTeaIdAndOrderId(teaId, orderId);
    }
}
