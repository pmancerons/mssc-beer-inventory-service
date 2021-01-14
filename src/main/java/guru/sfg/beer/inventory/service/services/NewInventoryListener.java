package guru.sfg.beer.inventory.service.services;

import curso.common.events.NewInventoryEvent;
import curso.common.model.BeerDto;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewInventoryListener {
    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void createNewInventoryForBeer(NewInventoryEvent event){
        BeerDto beerDto = event.getBeerDto();

        BeerInventory beerInventory = BeerInventory.builder()
                .beerId(beerDto.getId())
                .quantityOnHand(beerDto.getQuantityOnHand())
                .upc(beerDto.getUpc())
                .build();

        log.info("brewing beer " + beerDto.getBeerName() + " quantity " + beerDto.getQuantityOnHand());

        beerInventoryRepository.save(beerInventory);
    }
}
