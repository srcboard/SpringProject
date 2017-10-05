package com.labracode;

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

        User newUser = new User("Ivan2", "Ivanov", "Ivan2", "123");

        ObjectMapper mapper = new ObjectMapper();
        String[] customIgnorableField = {"id", "hashedPassword"};
        FilterProvider filters = new SimpleFilterProvider().
                addFilter("UserJSONFilter", SimpleBeanPropertyFilter.serializeAllExcept(customIgnorableField));

        String userJson = mapper.writer(filters).writeValueAsString(newUser);

        mockMvc.perform(post("/api/user")
                .contentType(contentType)
                .content(userJson))
                .andExpect(status().isOk());

    }

    @Test
    public void userAlreadyExists() throws Exception {

        User newUser = new User("Ivan", "Ivanov", "Ivan", "123");

        ObjectMapper mapper = new ObjectMapper();
        String[] customIgnorableField = {"id", "hashedPassword"};
        FilterProvider filters = new SimpleFilterProvider().
                addFilter("UserJSONFilter", SimpleBeanPropertyFilter.serializeAllExcept(customIgnorableField));

        String userJson = mapper.writer(filters).writeValueAsString(newUser);

        mockMvc.perform(post("/api/user")
                .contentType(contentType)
                .content(userJson))
                .andExpect(status().isConflict());

    }

}
