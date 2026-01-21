package enset.ma.orderservice.common_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateOrderRequestDTO {
    private String userId;
    private String shippingAddress;
    private List<OrderLineRequestDTO> orderItems;
}