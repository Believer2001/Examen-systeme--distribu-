package enset.ma.orderservice.query.repository;

import enset.ma.orderservice.query.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}

