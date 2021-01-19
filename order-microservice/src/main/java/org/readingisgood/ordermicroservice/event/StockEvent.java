package org.readingisgood.ordermicroservice.event;

import lombok.Data;

@Data
public class StockEvent {

    private String transactionId;
    private String reason;
    private StockEventType eventType;
    private long timestamp;

}
