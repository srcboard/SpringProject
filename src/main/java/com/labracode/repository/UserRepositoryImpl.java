package com.labracode.repository;

import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserRepositoryImpl implements UserRepository {

    private static ArrayList<User> userList = new ArrayList<User>();

    static {
        userList.add(new User("Ivan", "Ivanov", "Ivan", "123"));
        userList.add(new User("Petr", "Petrovich", "Petr", "123"));
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        userList.add(user);
        return user;
    }

    @Override
    public Boolean userExists(User user) {
        return userList.contains(user);
    }

}