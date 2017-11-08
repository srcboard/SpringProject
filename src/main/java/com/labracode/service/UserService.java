package com.labracode.service;

import com.labracode.dto.UserDTO;
import com.labracode.exceptions.UserAlreadyExistsException;

public interface UserService {

    UserDTO createUser(UserDTO user) throws UserAlreadyExistsException;

}