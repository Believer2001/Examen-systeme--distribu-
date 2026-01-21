package enset.ma.orderservice.common_api.event;

import enset.ma.orderservice.common_api.dtos.OrderLineRequestDTO;
import enset.ma.orderservice.common_api.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor  @Builder
public class OrderCreatedEvent {

    private String orderId;
    private String userId;
    private OrderStatus orderStatus;
    private String shippingAddress;
    private Date orderDate;
    private List<OrderLineRequestDTO> orderItems;

}
