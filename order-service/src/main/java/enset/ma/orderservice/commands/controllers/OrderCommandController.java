package enset.ma.orderservice.commands.controllers;
package enset.ma.orderservice.commands.controllers;

import enset.ma.orderservice.common_api.command.CreateOrderCommand;
import enset.ma.orderservice.common_api.dtos.CreateOrderRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/orders")
@AllArgsConstructor
public class OrderCommandController {
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<String> createOrder(@RequestBody CreateOrderRequestDTO request) {
        return commandGateway.send(new CreateOrderCommand(
                UUID.randomUUID().toString(),
                request.getShippingAddress(),
                request.getOrderItems()
        ));
    }
}
