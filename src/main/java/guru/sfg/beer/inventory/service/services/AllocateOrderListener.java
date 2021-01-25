package guru.sfg.beer.inventory.service.services;

import curso.common.events.AllocateOrderRequest;
import curso.common.events.AllocationOrderResult;
import curso.common.model.BeerOrderDto;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderListener {
    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ORDER_ALLOCATION_QUEUE)
    public void allocateOrder(AllocateOrderRequest allocateOrderRequest){
        BeerOrderDto beerOrder = allocateOrderRequest.getBeerOrderDto();

        AllocationOrderResult.AllocationOrderResultBuilder builder = AllocationOrderResult.builder()
                .beerOrderDto(beerOrder);

        try{
            builder.allocationError(false);
            builder.pendingInventory(!allocationService.allocateOrder(beerOrder));
        }catch(Exception ex){
            log.error(ex.getMessage());
            builder.allocationError(true);
        }

        jmsTemplate.convertAndSend(JmsConfig.ORDER_ALLOCATION_RESULT_QUEUE ,builder.build());

    }
}
