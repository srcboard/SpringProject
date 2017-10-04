package com.labracode.repository;

import com.labracode.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository {

    User createUser(User user) throws UserAlreadyExistsException;

    class UserAlreadyExistsException extends Exception {
    }

}