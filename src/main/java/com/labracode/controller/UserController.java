package com.labracode.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.ErrorMessage;
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

//    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
//    public ResponseEntity<User> userRegistration(@RequestBody User user) throws UserAlreadyExistsException {
//
//        if (userRepository.userExists(user)) {
//            throw new UserAlreadyExistsException(user.toString());
//        }
//
//        User createdUser = userRepository.createUser(user);
//        return new ResponseEntity<User>(createdUser, HttpStatus.OK);
//
//    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> userRegistration(@RequestBody User user) throws UserAlreadyExistsException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        if (userRepository.userExists(user)) {
            ErrorMessage errorMessage = new ErrorMessage("USER_ALREADY_EXISTS", "A user with the given username already exists");
            String messageAsString = mapper.writer().writeValueAsString(errorMessage);
            return new ResponseEntity<String>(messageAsString, HttpStatus.CONFLICT);
        }

        User createdUser = userRepository.createUser(user);

        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("password");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", theFilter);
        String userAsString = mapper.writer(filters).writeValueAsString(user);
        return new ResponseEntity<String>(userAsString, HttpStatus.OK);

    }

}
