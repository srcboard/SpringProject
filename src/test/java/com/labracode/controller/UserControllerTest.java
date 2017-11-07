package com.labracode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labracode.dto.UserDTO;
import com.labracode.model.ErrorMessage;
import com.labracode.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {

    private static final String API_USER_ENDPOINT = "/api/user";
    private static long USERNAME_SEQUENCE = 300;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).dispatchOptions(true).build();
    }

    @Test
    void successfulUserRegistration() throws Exception {
        createUser();
    }

    @Test
    void userAlreadyExists() throws Exception {
        UserDTO userDTO = new UserDTO(createUser());
        ErrorMessage errorDTO = new ErrorMessage();
        mockMvc.perform(post(API_USER_ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsBytes(userDTO)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.is(errorDTO.getCode())))
                .andExpect(jsonPath("$.description", Matchers.is(errorDTO.getDescription())));
    }

    private UserDTO getNewUniqueUser() {
        USERNAME_SEQUENCE++;
        return new UserDTO("FirstNameTest", "LastNameTest", "UserNameTest" + USERNAME_SEQUENCE, "PasswordTest");
    }

    private User createUser() throws Exception {

        UserDTO dto = getNewUniqueUser();

        HashSet<UserDTO> followers = new HashSet<>();
        followers.add(getNewUniqueUser());
        followers.add(getNewUniqueUser());
        followers.add(getNewUniqueUser());
        followers.add(getNewUniqueUser());

        byte[] response = mockMvc.perform(post(API_USER_ENDPOINT)
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.isA(Integer.class)))
                .andExpect(jsonPath("$.firstName", Matchers.is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", Matchers.is(dto.getLastName())))
                .andExpect(jsonPath("$.userName", Matchers.is(dto.getUserName())))
                .andReturn().getResponse().getContentAsByteArray();
        return mapper.readValue(response, User.class);

    }

}