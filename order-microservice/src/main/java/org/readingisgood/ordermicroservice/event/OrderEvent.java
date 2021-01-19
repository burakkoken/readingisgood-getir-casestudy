package org.readingisgood.ordermicroservice.event;

import lombok.Data;
import org.readingisgood.ordermicroservice.model.Order;

@Data
public class OrderEvent {

    private String transactionId;
    private Long bookId;
    private Long quantity;
    private Long userId;
    private OrderEventType eventType;
    private long timestamp = System.currentTimeMillis();

    private OrderEvent() {

    }

    public static OrderEvent of(Order order, OrderEventType eventType) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setTransactionId(order.getTransactionId());
        orderEvent.setBookId(order.getBookId());
        orderEvent.setQuantity(order.getQuantity());
        orderEvent.setUserId(order.getUserId());
        orderEvent.setEventType(eventType);
        return orderEvent;
    }

}
