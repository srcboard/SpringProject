package com.labracode.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("A user " + username + " already exists");
    }

}