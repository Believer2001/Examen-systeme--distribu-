package enset.ma.orderservice.common_api.event;

import enset.ma.orderservice.common_api.dtos.OrderLineRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
@Getter
@AllArgsConstructor
@Builder
public class OrderAddEvent {
    private String orderLineId;
   private OrderLineRequestDTO orderLine;
    private Date orderDate;
}
