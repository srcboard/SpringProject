package com.labracode.controller;

import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import com.labracode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<User> userRegistration(@RequestBody User user) throws UserAlreadyExistsException {

        if (userRepository.userExists(user)) {
            throw new UserAlreadyExistsException(user.toString());
        }

        User createdUser = userRepository.createUser(user);
        return new ResponseEntity<User>(createdUser, HttpStatus.OK);

    }

}
