package org.readingisgood.stockmicroservice.event;

import lombok.Data;

@Data
public class StockEvent {

    private String transactionId;
    private String reason;
    private StockEventType eventType;
    private long timestamp = System.currentTimeMillis();

    private StockEvent() {

    }

    public static StockEvent of(String transactionId, StockEventType stockEventType) {
        StockEvent stockEvent = new StockEvent();
        stockEvent.setTransactionId(transactionId);
        stockEvent.setEventType(stockEventType);
        return stockEvent;
    }

    public static StockEvent of(String transactionId, StockEventType stockEventType, String reason) {
        StockEvent stockEvent = StockEvent.of(transactionId, stockEventType);
        stockEvent.setReason(reason);
        return stockEvent;
    }

}
