package enset.ma.orderservice.commands.aggregate;
import enset.ma.orderservice.common_api.command.CreateOrderAddCommand;
import enset.ma.orderservice.common_api.command.CreateOrderCommand;
import enset.ma.orderservice.common_api.dtos.OrderLineRequestDTO;
import enset.ma.orderservice.common_api.enums.OrderStatus;
import enset.ma.orderservice.common_api.event.OrderAddEvent;
import enset.ma.orderservice.common_api.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;
import java.util.List;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String userId;
    private String shippingAddress;
    private OrderStatus status;
    private List<OrderLineRequestDTO> orderItems;
    private Date orderDate;

    public OrderAggregate() { }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        // Validation simple
        if (command.getOrderItems() == null || command.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Une commande doit avoir au moins un produit");
        }

        AggregateLifecycle.apply(OrderCreatedEvent.builder()
                .orderId(command.getOrderId())
                .userId(command.getUserId())
                .shippingAddress(command.getShippingAddress())
                .orderStatus(OrderStatus.CREATED)
                .orderDate(new Date())
                .build());
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.orderDate = event.getOrderDate();
        this.status = event.getOrderStatus();
        this.shippingAddress = event.getShippingAddress();
    }

    @CommandHandler
    public void handle(CreateOrderAddCommand Command)
    {
        // Validation simple
        if (Command.getOrderLineId() == null ) {
            throw new IllegalArgumentException("Une commande doit avoir au moins un produit");
        }

        if(Command.getOrder().equals(null))
        {
            throw new IllegalArgumentException("the product must not be null");
        }

        AggregateLifecycle.apply(OrderAddEvent.builder()
                .orderLineId(Command.getOrderLineId())
                .orderLine(Command.getOrder())
                .orderDate(new Date())
                .build());
    }

    @EventSourcingHandler
    public void on(OrderAddEvent event) {
        if (this.status == OrderStatus.CREATED) {
            this.status = OrderStatus.ACTIVATED;
        }


        this.orderItems.add(event.getOrderLine());
    }
}