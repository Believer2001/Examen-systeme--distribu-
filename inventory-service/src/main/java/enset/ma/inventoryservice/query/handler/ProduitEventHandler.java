package enset.ma.inventoryservice.query.handler;


import enset.ma.inventoryservice.common_api.event.ProduitCreatedEvent;
import enset.ma.inventoryservice.common_api.event.ProduitUpdatedEvent;
import enset.ma.inventoryservice.query.entities.Produit;
import enset.ma.inventoryservice.query.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class ProduitEventHandler {
    private final ProduitRepository produitRepository;

    @EventHandler
    public void on(ProduitCreatedEvent event) {
        log.info("Insertion d'un nouveau produit : {}", event.getId());

        Produit produit = new Produit();
        produit.setProduitId(event.getId()); // Utilisation de l'ID de l'event
        produit.setName(event.getName());
        produit.setPrice(event.getPrice());
        produit.setQuantity(event.getQuantity());
        // Attention : vérifiez que event.getState() existe ou initialisez par défaut
        // produit.setProduitState(event.getState());

        produitRepository.save(produit);
    }

    @EventHandler
    public void on(ProduitUpdatedEvent event) {
        log.info("Mise à jour du produit : {}", event.getId());

        if (event.getId() == null) {
            log.error("Erreur : ID nul reçu dans ProduitUpdatedEvent");
            return;
        }

        // Utilisation de findById de manière sécurisée
        produitRepository.findById(event.getId()).ifPresentOrElse(produit -> {
            produit.setName(event.getName());
            produit.setPrice(event.getPrice());
            produit.setQuantity(event.getQuantity());
            // Mise à jour de l'état si nécessaire
            produitRepository.save(produit);
            log.info("Produit {} mis à jour avec succès", event.getId());
        }, () -> {
            log.warn("Produit avec l'ID {} non trouvé en base Query", event.getId());
        });
    }
}