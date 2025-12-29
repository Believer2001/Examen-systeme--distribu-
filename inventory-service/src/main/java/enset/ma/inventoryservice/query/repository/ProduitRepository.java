package enset.ma.inventoryservice.query.repository;

import enset.ma.inventoryservice.query.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProduitRepository extends JpaRepository<Produit, String> {

}
