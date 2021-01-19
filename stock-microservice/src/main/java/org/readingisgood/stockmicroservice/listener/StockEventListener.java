package org.readingisgood.stockmicroservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.stockmicroservice.config.KafkaConfiguration;
import org.readingisgood.stockmicroservice.event.StockEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockEventListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public StockEventListener(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener(StockEvent.class)
    public void produceOrderEvent(StockEvent orderEvent) {
        kafkaTemplate.send(KafkaConfiguration.STOCK_EVENT_TOPIC, orderEvent);
        log.debug("Produced an Stock Event : {}", orderEvent.toString());
    }

}
