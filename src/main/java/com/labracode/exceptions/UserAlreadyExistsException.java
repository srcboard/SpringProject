package com.labracode.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String username) {
        super("A user " + username + " already exists");
    }

}