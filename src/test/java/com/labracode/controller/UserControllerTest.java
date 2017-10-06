package com.labracode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.labracode.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).dispatchOptions(true).build();
    }

    @Test
    public void successfulUserRegistration() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("id");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", theFilter);

        String userAsString = mapper.writer(filters).writeValueAsString(getNewUser());

        mockMvc.perform(post("/api/user")
                .contentType(contentType)
                .content(userAsString))
                .andExpect(status().isOk());

    }

    @Test
    public void userAlreadyExists() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("id");
        FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", theFilter);

        String userAsString = mapper.writer(filters).writeValueAsString(getExistingUser());

        mockMvc.perform(post("/api/user")
                .contentType(contentType)
                .content(userAsString))
                .andExpect(status().isConflict());

    }

    private User getExistingUser() {
        return new User("Ivan", "Ivanov", "Ivan", "123");
    }

    private User getNewUser() {
        return new User("Ivan2", "Ivanov", "Ivan2", "123");
    }

}
