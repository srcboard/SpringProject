package com.labracode.repository;

import com.labracode.dto.UserDTO;
import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@Service
public class UserRepositoryImpl implements UserRepository {

    private static ArrayList<User> userList = new ArrayList<User>();

    static {
        userList.add(new User("Ivan", "Ivanov", "Ivan", "123"));
        userList.add(new User("Petr", "Petrovich", "Petr", "123"));
    }

    @Override
    public UserDTO createUser(UserDTO user) throws UserAlreadyExistsException {

        if (isExistUserName(user.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException(user.toString());
        }

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUserName(user.getUserName());
        newUser.setPlainTextPassword(user.getPlainTextPassword());

        Random random = new Random();
        Integer id = random.nextInt(100) + 1;
        newUser.setId(id.toString());

        String textPassword = newUser.getPlainTextPassword() == null ? "" : newUser.getPlainTextPassword();
        String hashedPassword = Base64.getEncoder().encodeToString(textPassword.getBytes());
        newUser.setHashedPassword(hashedPassword);

        userList.add(newUser);

        user.setId(newUser.getId());
        user.setPlainTextPassword(null);

        return user;

    }

//    @Override
//    public Boolean userExists(User user) {
//        return userList.contains(user);
//    }

    private Optional<User> isExistUserName(String userName) {
        return userList.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst();
    }

}