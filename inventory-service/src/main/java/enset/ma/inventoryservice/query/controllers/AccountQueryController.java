package enset.ma.inventoryservice.query.controllers;


import enset.ma.inventoryservice.query.entities.Produit;
import enset.ma.inventoryservice.query.queries.GetAllProduitst;
import enset.ma.inventoryservice.query.queries.GetProduitById;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@RestController
@RequestMapping("/queries/produits")
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/all")
    public CompletableFuture<List<Produit>> getAllAccounts() {

        return queryGateway.query(new GetAllProduitst(),
                ResponseTypes.multipleInstancesOf(Produit.class));
    }
    @GetMapping("/{produitId}")
    public CompletableFuture<Produit> getAccountById(@PathVariable String accountId) {
        return queryGateway.query(
                new GetProduitById(accountId),
                ResponseTypes.instanceOf(Produit.class)
        );
    }


    @ExceptionHandler
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
