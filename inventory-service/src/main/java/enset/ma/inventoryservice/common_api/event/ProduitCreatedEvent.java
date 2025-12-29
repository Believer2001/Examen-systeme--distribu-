package enset.ma.inventoryservice.common_api.event;

import enset.ma.inventoryservice.common_api.enums.ProduitState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

@Getter
@AllArgsConstructor
public class ProduitCreatedEvent {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private ProduitState state;

}
