package enset.ma.inventoryservice.common_api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.checkerframework.checker.units.qual.N;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteProduitCommand {
    @TargetAggregateIdentifier
  String idProduit;
}
