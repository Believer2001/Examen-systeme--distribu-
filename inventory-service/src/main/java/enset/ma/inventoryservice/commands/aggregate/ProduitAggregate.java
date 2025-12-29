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
    private  String produitId ;
    private  String name ;
    private  double price ;
    private  int quantity ;
    private ProduitState ProduitState;




    //  construucteur san parametre obligatoirr pour axon
    public ProduitAggregate() {
        // requis par axon

    }

    //unconstucteur avec parametre pour l'initialisation des attributs de l'aggregate
   //  c est en meme temps le handler du command CreateAccountCommand

    @CommandHandler
    public ProduitAggregate(CreateProduitCommand command) {
        if(command.getName()==null){
            throw new IllegalArgumentException("Invalid ,le produit doit avoir au moins un nom ");
        }

        AggregateLifecycle.apply(new ProduitCreatedEvent(
                UUID.randomUUID().toString(),
                command.getName(),
                command.getPrice(),
                command.getQuantity(),
                ProduitState.DISPONIBLE
        ));


            }


            @EventSourcingHandler
     public  void handler (ProduitCreatedEvent event){
       this.produitId=event.getId();
         this.name=event.getName();
         this.price=event.getPrice();
         this.quantity=event.getQuantity();
         this.ProduitState=event.getState();
    }

    //  pour la mise a jour du status
    @CommandHandler
    public  void handler (UpdateProduitCommand command){

        ProduitUpdatedEvent produitUpdatedEvent=new ProduitUpdatedEvent();
        if(!command.getName().equals(this.name)){
            produitUpdatedEvent.setName(command.getName());
        }
        if(command.getPrice() != this.price){
            produitUpdatedEvent.setPrice(command.getPrice());
        }
        if(command.getQuantity() != this.quantity){
            produitUpdatedEvent.setQuantity(command.getQuantity());
        }
        if(command.getState() != this.ProduitState){
            produitUpdatedEvent.setState(command.getState());
        }

        AggregateLifecycle.apply( produitUpdatedEvent
        );
    }



    @EventSourcingHandler
    public  void on (ProduitCreatedEvent event){
        this.produitId=event.getId();
        this.name=event.getName();
        this.price=event.getPrice();
        this.quantity=event.getQuantity();
        this.ProduitState=event.getState();

    }



}
