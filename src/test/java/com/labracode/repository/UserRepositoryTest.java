package com.labracode.repository;

import com.labracode.dto.UserDTO;
import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void successfulCreateUser() {

        assertThrows(UserAlreadyExistsException.class, () -> {
            UserDTO user = userRepository.createUser(getExistingUser());
        });

    }

    @Test
    void failedCreateUser() {

        UserDTO user = userRepository.createUser(getNewUser());
        assertTrue(!user.getId().isEmpty());

    }

    private UserDTO getExistingUser() {
        return new UserDTO("Ivan", "Ivanov", "Ivan", "123");
    }

    private UserDTO getNewUser() {
        return new UserDTO("Ivan2", "Ivanov", "Ivan2", "123");
    }

}