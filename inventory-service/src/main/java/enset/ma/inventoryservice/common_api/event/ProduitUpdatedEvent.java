package enset.ma.inventoryservice.common_api.event;

import enset.ma.inventoryservice.common_api.enums.ProduitState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class ProduitUpdatedEvent {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private ProduitState state;

}
