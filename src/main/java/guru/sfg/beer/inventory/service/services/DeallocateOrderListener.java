package guru.sfg.beer.inventory.service.services;

import curso.common.events.DeallocateOrderRequest;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeallocateOrderListener {
    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.ORDER_DEALLOCATION_QUEUE)
    public void allocateOrder(DeallocateOrderRequest deallocateOrderRequest){
        allocationService.deallocateOrder(deallocateOrderRequest.getBeerOrderDto());
    }
}
