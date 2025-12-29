package enset.ma.inventoryservice.commands.aggregate;


import enset.ma.accountservicemicroservice.common_api.command.CreateAccountCommand;
import enset.ma.accountservicemicroservice.common_api.command.CreditAccountCommand;
import enset.ma.accountservicemicroservice.common_api.command.DebitAccountCommand;
import enset.ma.accountservicemicroservice.common_api.command.UpdateAccountStatusCommand;
import enset.ma.accountservicemicroservice.common_api.enums.AccountStatus;
import enset.ma.accountservicemicroservice.common_api.event.AccountCreatedEvent;
import enset.ma.accountservicemicroservice.common_api.event.AccountUpdateStatusEvent;
import enset.ma.accountservicemicroservice.common_api.event.CreditAccountEvent;
import enset.ma.accountservicemicroservice.common_api.event.DebitAccountEvent;
import enset.ma.inventoryservice.common_api.enums.ProduitState;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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
    public ProduitAggregate(CreateAccountCommand command) {
        if(command.getBalance()<=0){
            throw new IllegalArgumentException("Invalid initial balance, must be non negative");
        }

        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));

        AggregateLifecycle.apply(new AccountUpdateStatusEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));
            }


            @EventSourcingHandler
    public  void handler (AccountCreatedEvent event){
        this.accountId=event.getId();
        this.balance=event.getInitialBalance();
        this.currency=event.getCurrency();
        this.accountStatus=event.getStatus();
    }



    // commande  handler pour gerer le debit

    @CommandHandler
    public void handler(CreditAccountCommand command){
        if(command.getAmount()<=0){
            throw new IllegalArgumentException("Invalid amount, must be non negative");
        }
        if(this.accountStatus!= AccountStatus.ACTIVATED){
            throw new IllegalArgumentException("Account is not activated");
        }

        AggregateLifecycle.apply( new CreditAccountCommand(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    // pour faire evoluer l'aggregate apres l'evenement de credit
    @EventSourcingHandler
    public  void handler(CreditAccountEvent event){
        this.balance+=event.getAmount();
        this.currency=event.getCurrency();
    }

    @CommandHandler
    public void handler(DebitAccountCommand command){
        if(command.getAmount()<=0){
            throw new IllegalArgumentException("Invalid amount, must be non negative");
        }
        if(this.balance<command.getAmount()){
            throw new IllegalArgumentException("Insufficient balance");
        }
        if(this.accountStatus!= AccountStatus.ACTIVATED){
            throw new IllegalArgumentException("Account is not activated");
        }

        AggregateLifecycle.apply( new DebitAccountEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public  void on(DebitAccountEvent event){
        this.balance-=event.getAmount();
        this.currency=event.getCurrency();
    }


    //  pour la mise a jour du status
    @CommandHandler
    public  void handler (UpdateAccountStatusCommand command){
        if(command.getStatus()==null){
            throw new IllegalArgumentException("Invalid status");
        }
        if (this.accountStatus.equals(command.getStatus())){
            throw new IllegalArgumentException("Account is already in the status: " + command.getStatus());
        }
        AggregateLifecycle.apply(new AccountUpdateStatusEvent(
                command.getId(),
                command.getStatus()
        ));
    }



    @EventSourcingHandler
    public  void on (AccountUpdateStatusEvent event){
        this.accountStatus=event.getStatus();
    }


}
