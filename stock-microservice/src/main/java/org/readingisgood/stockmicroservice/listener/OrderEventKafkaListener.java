package org.readingisgood.stockmicroservice.listener;

import lombok.extern.slf4j.Slf4j;
import org.readingisgood.stockmicroservice.config.KafkaConfiguration;
import org.readingisgood.stockmicroservice.event.OrderEvent;
import org.readingisgood.stockmicroservice.service.StockService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventKafkaListener {

    private StockService stockService;

    public OrderEventKafkaListener(StockService stockService) {
        this.stockService = stockService;
    }

    @KafkaListener(topics = KafkaConfiguration.ORDER_EVENT_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderEvent(@Payload OrderEvent orderEvent) {
        stockService.updateStockQuantity(orderEvent);
        log.debug("Consumed order event : {}", orderEvent.toString());
    }

}
