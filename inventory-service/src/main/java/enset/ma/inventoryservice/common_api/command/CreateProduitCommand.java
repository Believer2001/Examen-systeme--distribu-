package enset.ma.inventoryservice.common_api.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateProduitCommand {
   @TargetAggregateIdentifier
    private  String commandId ;
    private  String name ;
    private  double price ;
    private  int quantity ;


}
