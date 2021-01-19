package org.readingisgood.authmicroservice.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String email) {
        super("User already registered : " + email);
    }

}