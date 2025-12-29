package enset.ma.inventoryservice.common_api.dtos;

import enset.ma.inventoryservice.common_api.enums.ProduitState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateProduitRequestDTO {

    private  String name ;
    private  double price ;
    private  int quantity ;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


}
