package org.readingisgood.ordermicroservice.exception;

public class OrderNotFoundException extends RuntimeException {

    private OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException of(String transactionId) {
        return new OrderNotFoundException("Order not found : " + transactionId);
    }

}
