package me.zuif.teashop.service.impl;

import me.zuif.teashop.dto.OrderDTO;
import me.zuif.teashop.model.order.Order;
import me.zuif.teashop.model.order.OrderDetails;
import me.zuif.teashop.repository.OrderRepository;
import me.zuif.teashop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsService orderDetailsService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailsService orderDetailsService) {
        this.orderRepository = orderRepository;
        this.orderDetailsService = orderDetailsService;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(String id) {
        orderRepository.delete(findById(id).get());
    }


    @Override
    public Page<Order> findAll(PageRequest request) {
        return orderRepository.findAll(request);
    }

    @Override
    public Optional<OrderDTO> getDTO(String id) {
        Optional<Order> orderOptional = findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            OrderDTO result = new OrderDTO();
            result.setOrder(order);
            Map<String, OrderDetails> detailsMap = new HashMap<>();
            for (OrderDetails details : order.getDetails()) {
                detailsMap.put(details.getTea().getId(), details);
            }
            result.setDetailsMap(detailsMap);
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<Order> findAllByUserId(String userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }
}
