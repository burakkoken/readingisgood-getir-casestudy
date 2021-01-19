package org.readingisgood.ordermicroservice.exception;

public class TokenInvalidException extends RuntimeException {

    private TokenInvalidException(String message) {
        super(message);
    }

    public static TokenInvalidException of(String message) {
        return new TokenInvalidException(message);
    }

}
