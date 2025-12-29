package enset.ma.inventoryservice.common_api.dtos;

import enset.ma.inventoryservice.common_api.enums.ProduitState;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Id
public class UpdateProduitRequestDTO {
    private  String name ;
    private  double price ;
    private  int quantity ;
    private ProduitState ProduitState;
}
