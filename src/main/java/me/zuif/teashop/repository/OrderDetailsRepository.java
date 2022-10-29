package me.zuif.teashop.repository;

import me.zuif.teashop.model.order.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    OrderDetails findByTeaIdAndOrderId(String teaId, String orderId);
}
