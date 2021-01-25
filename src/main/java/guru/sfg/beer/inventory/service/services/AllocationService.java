package guru.sfg.beer.inventory.service.services;

import curso.common.model.BeerOrderDto;

public interface AllocationService {
    Boolean allocateOrder(BeerOrderDto beerOrderDto);
}
