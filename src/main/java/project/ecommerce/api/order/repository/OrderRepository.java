package project.ecommerce.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
