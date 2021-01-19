package org.readingisgood.stockmicroservice.publisher;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.stockmicroservice.event.StockEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(StockEvent stockEvent) {
        applicationEventPublisher.publishEvent(stockEvent);
        log.debug("StockEvent is published : {}", stockEvent);
    }

}
