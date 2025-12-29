package enset.ma.orderservice.common_api.command;
import enset.ma.orderservice.common_api.dtos.OrderLineRequestDTO;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String shippingAddress;
    private List<OrderLineRequestDTO> orderItems;
}

event/OrderCreatedEvent.java
        Java

package enset.ma.orderservice.common_api.event;

import enset.ma.orderservice.common_api.dtos.OrderLineRequestDTO;
import enset.ma.orderservice.common_api.enums.OrderStatus;
import lombok.*;
        import java.util.Date;
import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderCreatedEvent {
    private String orderId;
    private Date orderDate;
    private String shippingAddress;
    private OrderStatus status;
    private List<OrderLineRequestDTO> orderItems;
}