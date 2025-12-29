package enset.ma.orderservice.commands.aggregate;
import enset.ma.orderservice.common_api.command.CreateOrderCommand;
import enset.ma.orderservice.common_api.enums.OrderStatus;
import enset.ma.orderservice.common_api.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private OrderStatus status;
    private String shippingAddress;

    public OrderAggregate() { }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        // Validation simple
        if (command.getOrderItems() == null || command.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Une commande doit avoir au moins un produit");
        }

        AggregateLifecycle.apply(OrderCreatedEvent.builder()
                .orderId(command.getOrderId())
                .orderDate(new Date())
                .shippingAddress(command.getShippingAddress())
                .status(OrderStatus.CREATED)
                .orderItems(command.getOrderItems())
                .build());
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.status = event.getStatus();
        this.shippingAddress = event.getShippingAddress();
    }
}