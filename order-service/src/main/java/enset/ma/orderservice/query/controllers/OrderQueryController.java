package enset.ma.orderservice.query.controllers;

import enset.ma.orderservice.query.entities.Order;
import enset.ma.orderservice.query.queries.GetAllOrders;
import enset.ma.orderservice.query.queries.GetOrderById;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/queries/orders")
@AllArgsConstructor
public class OrderQueryController {
    private final QueryGateway queryGateway;

    @GetMapping("/all")
    public CompletableFuture<List<Order>> getAllOrders() {
        return queryGateway.query(new GetAllOrders(),
                ResponseTypes.multipleInstancesOf(Order.class));
    }

    @GetMapping("/{orderId}")
    public CompletableFuture<Order> getOrderById(@PathVariable String orderId) {
        return queryGateway.query(new GetOrderById(orderId),
                ResponseTypes.instanceOf(Order.class));
    }
}

