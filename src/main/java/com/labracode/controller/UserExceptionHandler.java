package com.labracode.controller;

import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage userAlreadyExistsException() {
        ErrorMessage errorMessage = new ErrorMessage("USER_ALREADY_EXISTS", "A user with the given username already exists");
        return errorMessage;
    }

}
