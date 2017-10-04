package com.labracode.controller;

import com.labracode.model.User;
import com.labracode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User userRegistration(@RequestBody User user) throws UserRepository.UserAlreadyExistsException {

        userRepository.createUser(user);
        return user;

    }

}
