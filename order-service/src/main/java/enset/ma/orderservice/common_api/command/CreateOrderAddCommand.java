package enset.ma.orderservice.common_api.command;

import enset.ma.orderservice.common_api.dtos.OrderLineRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class CreateOrderAddCommand {
    @TargetAggregateIdentifier
    private String orderLineId;
    private OrderLineRequestDTO order;
    private Date orderDate;


}