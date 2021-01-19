package org.readingisgood.ordermicroservice.exception;

public class StockNotEnoughException extends RuntimeException {

    private StockNotEnoughException(String message) {
        super(message);
    }

    public static StockNotEnoughException of(Long bookId) {
        return new StockNotEnoughException("Stock is not enough : " + bookId);
    }

}
