package com.labracode.repository;

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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void successfulCreateUser() {

        assertThrows(UserAlreadyExistsException.class, () -> {
            User user = userRepository.createUser(getExistingUser());
        });

    }

    @Test
    public void failedCreateUser() {

        User user = userRepository.createUser(getNewUser());
        assertTrue(!user.getId().isEmpty());

    }

    private User getExistingUser() {
        return new User("Ivan", "Ivanov", "Ivan", "123");
    }

    private User getNewUser() {
        return new User("Ivan2", "Ivanov", "Ivan2", "123");
    }

}