package enset.ma.orderservice.common_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineRequestDTO {
    private String productId;
    private int quantity;
    private double unitPrice;
    private double discount;

}
