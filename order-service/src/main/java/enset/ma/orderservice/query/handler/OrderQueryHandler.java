package enset.ma.orderservice.query.handler;

import enset.ma.orderservice.common_api.event.OrderCreatedEvent;
import enset.ma.orderservice.query.entities.Order;
import enset.ma.orderservice.query.entities.OrderItems;
import enset.ma.orderservice.query.queries.GetAllOrders;
import enset.ma.orderservice.query.queries.GetOrderById;
import enset.ma.orderservice.query.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderQueryHandler {
    private final OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        log.info("Projection de la commande : {}", event.getOrderId());

        Order order = Order.builder()
                .id(event.getOrderId())
                .orderDate(event.getOrderDate())
                .shippingAddress(event.getShippingAddress())
                .status(event.getOrderStatus())
                .orderItems(new ArrayList<>())
                .build();

        // Transformation des DTOs de l'événement en Entités JPA
        event.getOrderItems().forEach(itemDto -> {
            OrderItems item = OrderItems.builder()
                    .productId(itemDto.getProductId())
                    .quantity(itemDto.getQuantity())
                    .unitPrice(itemDto.getUnitPrice())
                    .discount(itemDto.getDiscount())
                    .order(order)
                    .build();
            order.getOrderItems().add(item);
        });

        orderRepository.save(order);
    }

    @QueryHandler
    public List<Order> handle(GetAllOrders query) {
        return orderRepository.findAll();
    }

    @QueryHandler
    public Order handle(GetOrderById query) {
        return orderRepository.findById(query.getOrderId())
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }
}