package com.labracode.repository;

import com.labracode.dto.UserDTO;
import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;

public interface UserRepository {

    UserDTO createUser(UserDTO user) throws UserAlreadyExistsException;
//    Boolean userExists(UserDTO user);

}