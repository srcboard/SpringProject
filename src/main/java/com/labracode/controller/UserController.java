package com.labracode.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> userRegistration(@RequestBody User InputUserDTO) throws UserAlreadyExistsException, JsonProcessingException {

        if (userRepository.userExists(InputUserDTO)) {
            ErrorMessage errorMessage = new ErrorMessage("USER_ALREADY_EXISTS", "A user with the given username already exists");
            String messageAsString = mapper.writer().writeValueAsString(errorMessage);
            return new ResponseEntity<String>(messageAsString, HttpStatus.CONFLICT);
        }

        User createdUser = userRepository.createUser(InputUserDTO);
        String userAsString = mapper.writer().writeValueAsString(createdUser.getOutputDTO());
        return new ResponseEntity<String>(userAsString, HttpStatus.OK);

    }

}
