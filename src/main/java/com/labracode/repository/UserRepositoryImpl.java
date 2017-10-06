package com.labracode.repository;

import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

@Service
public class UserRepositoryImpl implements UserRepository {

    private static ArrayList<User> userList = new ArrayList<User>();

    static {
        userList.add(new User("Ivan", "Ivanov", "Ivan", "123"));
        userList.add(new User("Petr", "Petrovich", "Petr", "123"));
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {

        if (userExists(user)) {
            throw new UserAlreadyExistsException(user.toString());
        }

        Random random = new Random();
        Integer id = random.nextInt(100) + 1;
        user.setId(id.toString());

        String textPassword = user.getPlainTextPassword() == null ? "" : user.getPlainTextPassword();
        String hashedPassword = Base64.getEncoder().encodeToString(textPassword.getBytes());
        user.setHashedPassword(hashedPassword);

        userList.add(user);
        return user;
    }

    @Override
    public Boolean userExists(User user) {
        return userList.contains(user);
    }

}