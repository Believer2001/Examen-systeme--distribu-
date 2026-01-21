package enset.ma.orderservice.query.entities;

import enset.ma.orderservice.common_api.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") // "order" est un mot réservé en SQL
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Order {
    @Id
    private String id;
    private Date orderDate;
    private Date deliveryDate;
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItems> orderItems;
}
