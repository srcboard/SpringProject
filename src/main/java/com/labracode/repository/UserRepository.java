package com.labracode.repository;

import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;

public interface UserRepository {

    User createUser(User user) throws UserAlreadyExistsException;
    Boolean userExists(User user);

}