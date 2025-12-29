package enset.ma.inventoryservice.query.entities;

import enset.ma.inventoryservice.common_api.enums.ProduitState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class Produit {
    @Id
    private  String produitId ;
    private  String name ;
    private  double price ;
    private  int quantity ;
    private ProduitState ProduitState;

}
