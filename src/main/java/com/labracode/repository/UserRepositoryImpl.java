package com.labracode.repository;

import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        return user;
    }

    @Override
    public Boolean userExists(User user) {
        return true;
    }

}