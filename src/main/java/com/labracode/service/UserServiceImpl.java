package com.labracode.service;

import com.labracode.dto.UserDTO;
import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import com.labracode.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO user) throws UserAlreadyExistsException {

        if (isExistUserName(user)) {
            throw new UserAlreadyExistsException(user.toString());
        }

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUserName(user.getUserName());
        newUser.setPlainTextPassword(user.getPlainTextPassword());

        String textPassword = newUser.getPlainTextPassword() == null ? "" : newUser.getPlainTextPassword();
        String hashedPassword = Base64.getEncoder().encodeToString(textPassword.getBytes());
        newUser.setHashedPassword(hashedPassword);

        userRepository.save(newUser);

        user.setId(newUser.getId());
        user.setPlainTextPassword(null);

        return user;

    }

    private boolean isExistUserName(UserDTO userDTO) {
        return userRepository.findByUserName(userDTO.getUserName()) != null;
    }

}