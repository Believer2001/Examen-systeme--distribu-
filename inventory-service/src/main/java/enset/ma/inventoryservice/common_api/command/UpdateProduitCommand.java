package enset.ma.inventoryservice.common_api.command;

import enset.ma.inventoryservice.common_api.enums.ProduitState;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
public class UpdateProduitCommand {
    @TargetAggregateIdentifier
    private  String commandId ;
    private  String name ;
    private  double price ;
    private  int quantity ;
    private ProduitState state ;

}
