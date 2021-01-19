package org.readingisgood.stockmicroservice.exception;

public class StockNotFoundException extends RuntimeException {

    private StockNotFoundException(String message) {
        super(message);
    }

    public static StockNotFoundException of(Long bookId) {
        return new StockNotFoundException("Stock data not found for the book : " + bookId);
    }

}
