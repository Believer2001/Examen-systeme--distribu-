package enset.ma.orderservice.commands.controllers;

import enset.ma.orderservice.common_api.command.CreateOrderCommand;
import enset.ma.orderservice.common_api.command.CreateOrderAddCommand;
import enset.ma.orderservice.common_api.dtos.CreateOrderRequestDTO;
import enset.ma.orderservice.common_api.dtos.OrderLineRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
                request.getUserId(),
                request.getShippingAddress(),
                request.getOrderItems()
        ));


    }

    @PostMapping("/Ajoutercommande/{orderId}")
    public CompletableFuture<String> ajouterCommande(@PathVariable String orderId, @RequestBody OrderLineRequestDTO orderLineRequestDTO) {
        // Création de la commande
        return commandGateway.send(new CreateOrderAddCommand(
                orderId,
               orderLineRequestDTO,
                new java.util.Date()

        ));

    }

    @PostMapping("/ajouterCommamndeListe/{orderId}" )

    public CompletableFuture<String> ajouterCommandeListe(@PathVariable String orderId, @RequestBody List<OrderLineRequestDTO> orderLineRequestDTOs) {
        CompletableFuture<String> result = null;
        for (OrderLineRequestDTO dto : orderLineRequestDTOs) {
            result = commandGateway.send(new CreateOrderAddCommand(
                    orderId,
                    dto,
                    new java.util.Date()
            ));
        }
        return result;
    }
}
