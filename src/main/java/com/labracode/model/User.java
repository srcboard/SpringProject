package com.labracode.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.labracode.dto.UserDTO;

import javax.persistence.*;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    private String lastName;

    private String userName;

    private String plainTextPassword;

    private String hashedPassword;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> followers;

    public User() {
    }

    public User(UserDTO userDTO) {
        setFirstName(userDTO.getFirstName());
        setLastName(userDTO.getLastName());
        setUserName(userDTO.getUserName());
        setPlainTextPassword(userDTO.getPlainTextPassword());
        encodePassword();

        setFollowers(userDTO.getFollowers().stream()
                .map(dto -> new User(dto.getFirstName(), dto.getLastName(), dto.getUserName(), dto.getPlainTextPassword()))
                .collect(Collectors.toSet()));
    }

    public User(String firstName, String lastName, String userName, String plainTextPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.plainTextPassword = plainTextPassword;
        encodePassword();
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
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

    @JsonProperty("userName")
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

    @JsonIgnore
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        User that = (User) obj;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getFirstName())
                .append(" ")
                .append(getLastName())
                .toString();
    }

    private void encodePassword() {
        String textPassword = getPlainTextPassword() == null ? "" : getPlainTextPassword();
        setHashedPassword(Base64.getEncoder().encodeToString(textPassword.getBytes()));
    }

}