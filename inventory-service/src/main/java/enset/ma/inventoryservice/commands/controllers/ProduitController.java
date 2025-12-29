package enset.ma.inventoryservice.commands.controllers;

import enset.ma.inventoryservice.common_api.command.CreateProduitCommand;
import enset.ma.inventoryservice.common_api.command.DeleteProduitCommand;
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


    @PutMapping("/udateProduit/{idProduit}")
    public CompletableFuture<String> creditAccount( @PathVariable String idProduit,@RequestBody UpdateProduitRequestDTO requestDTO){

        CompletableFuture<String> result = commandGateway.send(new UpdateProduitCommand(
                idProduit,
                requestDTO.getName(),
                requestDTO.getPrice(),
                requestDTO.getQuantity(),
                requestDTO.getProduitState()

        ));
        return result;
    }

    @DeleteMapping
    public CompletableFuture<String> deleteProduit( @RequestParam String idProduit){

        CompletableFuture<String> result = commandGateway.send(new DeleteProduitCommand(
                idProduit
        ));
        return result;
    }




    @GetMapping("/eventStore/{accountId}")
    public Stream getEvents(@PathVariable String produitId){
        return eventStore.readEvents(produitId).asStream();

    }



    // pour caper les execptioon is
    @ExceptionHandler
    public String  handleException(Exception e){
        return e.getMessage();
    }
}
