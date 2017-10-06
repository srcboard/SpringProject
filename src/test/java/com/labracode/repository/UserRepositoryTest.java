package com.labracode.repository;

import com.labracode.exceptions.UserAlreadyExistsException;
import com.labracode.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test(expected = UserAlreadyExistsException.class)
    public void successfulCreateUser() {
        User user = userRepository.createUser(getExistingUser());
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
