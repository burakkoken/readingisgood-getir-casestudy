package org.readingisgood.ordermicroservice.exception;

public class StockDataNotObtainedException extends RuntimeException {

    private StockDataNotObtainedException(String message) {
        super(message);
    }

    public static StockDataNotObtainedException of(Long bookId) {
        return new StockDataNotObtainedException("Book stock data not obtained: " + bookId);
    }

}