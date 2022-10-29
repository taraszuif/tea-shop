package me.zuif.teashop.service;

import me.zuif.teashop.dto.OrderDTO;
import me.zuif.teashop.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IOrderService {
    void save(Order order);

    void delete(String id);

    Page<Order> findAll(PageRequest request);

    Optional<OrderDTO> getDTO(String id);

    Page<Order> findAllByUserId(String userId, Pageable pageable);

    Optional<Order> findById(String id);

}
