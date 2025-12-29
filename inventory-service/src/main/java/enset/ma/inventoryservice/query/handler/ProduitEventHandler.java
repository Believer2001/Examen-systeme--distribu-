package enset.ma.inventoryservice.query.handler;


import enset.ma.inventoryservice.common_api.event.ProduitCreatedEvent;
import enset.ma.inventoryservice.common_api.event.ProduitUpdatedEvent;
import enset.ma.inventoryservice.query.entities.Produit;
import enset.ma.inventoryservice.query.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProduitEventHandler {
    private  final ProduitRepository produitRepository;

   @EventHandler
    public void  on(ProduitCreatedEvent event, EventMessage<ProduitUpdatedEvent> eventMessage) {
    log.info("=============== insertiing new event accaunt {} ==================", event.getId());

        Produit produit= new Produit();
        produit.setName(event.getName());
        produit.setPrice(event.getPrice());
        produit.setQuantity(event.getQuantity());
        produit.setProduitId(event.getId());
        produit.setProduitState(produit.getProduitState());
        produitRepository.save(produit);


    }
 @EventHandler
    public  void on(ProduitUpdatedEvent event, EventMessage<ProduitUpdatedEvent> eventMessage) {
        log.info("=======insertion activation de compte ========");


        Produit produit= produitRepository.findById(event.getId()).get();

        produit.setName(event.getName());
        produit.setPrice(event.getPrice());
        produit.setQuantity(event.getQuantity());
        produit.setProduitId(event.getId());
        produit.setProduitState(produit.getProduitState());
        produitRepository.save(produit);

    }



}
