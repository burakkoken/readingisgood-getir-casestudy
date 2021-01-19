package org.readingisgood.stockmicroservice.event;

import lombok.Data;

@Data
public class OrderEvent {

    private String transactionId;
    private Long bookId;
    private Long quantity;
    private Long userId;
    private OrderEventType eventType;

}
