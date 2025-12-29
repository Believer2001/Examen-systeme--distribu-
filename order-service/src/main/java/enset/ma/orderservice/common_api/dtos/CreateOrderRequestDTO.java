package enset.ma.orderservice.common_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderLineRequestDTO {
    private String productId;
    private int quantity;
    private double unitPrice;
    private double discount;
}

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateOrderRequestDTO {
    private String shippingAddress;
    private List<OrderLineRequestDTO> orderItems;
}