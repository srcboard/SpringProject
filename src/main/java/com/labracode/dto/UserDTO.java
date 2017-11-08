package com.labracode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.labracode.model.User;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    @JsonProperty("password")
    private String plainTextPassword;
    private Set<UserDTO> followers;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String userName, String plainTextPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.plainTextPassword = plainTextPassword;
    }

    public UserDTO(User user) {
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setUserName(user.getUserName());
        setPlainTextPassword(user.getPlainTextPassword());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }

    public Set<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserDTO> followers) {
        this.followers = followers;
    }

}
