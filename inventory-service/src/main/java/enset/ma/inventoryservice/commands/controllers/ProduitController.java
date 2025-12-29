package enset.ma.inventoryservice.commands.controllers;


import enset.ma.accountservicemicroservice.common_api.command.CreateAccountCommand;
import enset.ma.accountservicemicroservice.common_api.command.CreditAccountCommand;
import enset.ma.accountservicemicroservice.common_api.command.DebitAccountCommand;
import enset.ma.accountservicemicroservice.common_api.command.UpdateAccountStatusCommand;
import enset.ma.accountservicemicroservice.common_api.dtos.CreateAccountRequestDTO;
import enset.ma.accountservicemicroservice.common_api.dtos.CreditAccountRequestDTO;
import enset.ma.accountservicemicroservice.common_api.dtos.DebitAccountRequestDTO;
import enset.ma.accountservicemicroservice.common_api.dtos.UpdateStatusRequestDTO;
import enset.ma.inventoryservice.common_api.command.CreateProduitCommand;
import enset.ma.inventoryservice.common_api.command.UpdateProduitCommand;
import enset.ma.inventoryservice.common_api.dtos.CreateProduitRequestDTO;
import enset.ma.inventoryservice.common_api.dtos.UpdateProduitRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/produits")
@AllArgsConstructor
public class ProduitController {

    CommandGateway commandGateway;
    EventStore eventStore;



    @PostMapping("/create")
    public  CompletableFuture<String> CreeateProduit(@RequestBody CreateProduitRequestDTO requestDTO) {


        CompletableFuture<String> result= commandGateway.send(new CreateProduitCommand(
                UUID.randomUUID().toString(),
                requestDTO.getName(),
                requestDTO.getPrice(),
                requestDTO.getQuantity()
        ));

        return result;
    }


    @PostMapping("/udateProduit/{id}")
    public CompletableFuture<String> creditAccount(@RequestBody UpdateProduitRequestDTO requestDTO){

        CompletableFuture<String> result = commandGateway.send(new UpdateProduitCommand(
                 id ,
                requestDTO.,
                requestDTO.getAmount(),
                requestDTO.getCurrency()
        ));
        return result;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO requestDTO){
        CompletableFuture<String> result= commandGateway.send(  new DebitAccountCommand(
                requestDTO.getAccountId(),
                requestDTO.getAmount(),
                requestDTO.getCurrency()
        ) );
        return result;
    }


    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateAccountStatus(@RequestBody UpdateStatusRequestDTO requestDTO){
        CompletableFuture<String> result= commandGateway.send(
                new UpdateAccountStatusCommand(
                        requestDTO.getAccountId(),
                        requestDTO.getStatus()
                )
        );

        return result;
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream getEvents(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();

    }



    // pour caper les execptioon is
    @ExceptionHandler
    public String  handleException(Exception e){
        return e.getMessage();
    }
}
