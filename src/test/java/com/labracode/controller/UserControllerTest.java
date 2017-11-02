package com.labracode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labracode.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

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

        String userAsString = mapper.writer().writeValueAsString(getNewUser());
        mockMvc.perform(post("/api/user")
                .contentType(contentType)
                .content(userAsString))
                .andExpect(status().isOk());

    }

    @Test
    void userAlreadyExists() throws Exception {

        String userAsString = mapper.writer().writeValueAsString(getExistingUser());
        mockMvc.perform(post("/api/user")
                .contentType(contentType)
                .content(userAsString))
                .andExpect(status().isConflict());

    }

    private UserDTO getExistingUser() {
        return new UserDTO("Ivan", "Ivanov", "Ivan", "123");
    }

    private UserDTO getNewUser() {
        return new UserDTO("Ivan2", "Ivanov", "Ivan2", "123");
    }

}