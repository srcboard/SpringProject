package com.labracode.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labracode.dto.UserDTO;
import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userRepository;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, produces = "application/json")
    public UserDTO userRegistration(@RequestBody UserDTO user) throws UserAlreadyExistsException, JsonProcessingException {
        return userRepository.createUser(user);
    }

}
