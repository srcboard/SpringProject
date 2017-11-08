package com.labracode.repo;

import com.labracode.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT user FROM User user WHERE user.userName = :name")
    User findByUserName(@Param(value = "name") String userName);

    @Query(value = "SELECT user FROM User user WHERE user.userName = :name")
    User findFirst();

}
