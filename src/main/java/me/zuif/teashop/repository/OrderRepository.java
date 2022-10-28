package me.zuif.teashop.repository;

import me.zuif.teashop.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByUserId(String userId, Pageable pageable);

    Optional<Order> findById(String id);


}
