package enset.ma.inventoryservice.commands.aggregate;

import enset.ma.inventoryservice.common_api.command.CreateProduitCommand;
import enset.ma.inventoryservice.common_api.command.UpdateProduitCommand;
import enset.ma.inventoryservice.common_api.enums.ProduitState;
import enset.ma.inventoryservice.common_api.event.ProduitCreatedEvent;
import enset.ma.inventoryservice.common_api.event.ProduitUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class ProduitAggregate {

    @AggregateIdentifier
    private String produitId;
    private String name;
    private double price;
    private int quantity;
    private ProduitState produitState; // Correction nommage (camelCase)

    // Constructeur sans paramètre obligatoire pour Axon
    public ProduitAggregate() {
    }

    // Handler pour la création
    @CommandHandler
    public ProduitAggregate(CreateProduitCommand command) {
        if (command.getName() == null || command.getName().isEmpty()) {
            throw new IllegalArgumentException("Le produit doit avoir un nom");
        }

        AggregateLifecycle.apply(new ProduitCreatedEvent(
                UUID.randomUUID().toString(),
                command.getName(),
                command.getPrice(),
                command.getQuantity(),
                ProduitState.DISPONIBLE
        ));
    }

    // Handler pour la mise à jour
    @CommandHandler
    public void handle(UpdateProduitCommand command) {
        // On prépare l'événement avec les nouvelles valeurs
        // Note: On passe systématiquement les valeurs de la commande ou les anciennes
        ProduitUpdatedEvent event = new ProduitUpdatedEvent();
        event.setId(this.produitId); // Très important : ne pas perdre l'ID
        event.setName(command.getName());
        event.setPrice(command.getPrice());
        event.setQuantity(command.getQuantity());
        event.setState(command.getState());

        AggregateLifecycle.apply(event);
    }

    // --- Event Sourcing Handlers (Mise à jour de l'état interne) ---

    @EventSourcingHandler
    public void on(ProduitCreatedEvent event) {
        this.produitId = event.getId();
        this.name = event.getName();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
        this.produitState = event.getState();
    }

    @EventSourcingHandler
    public void on(ProduitUpdatedEvent event) {
        // Obligatoire pour que l'agrégat reste à jour après un update
        this.name = event.getName();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
        this.produitState = event.getState();
    }
}