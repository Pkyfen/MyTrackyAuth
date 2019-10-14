package ru.mytracky;


import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mytracky.controller.RegistrationControllerV1;
import ru.mytracky.dto.AuthenticationRequestDto;
import ru.mytracky.dto.RegistrationUserDto;
import ru.mytracky.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.mytracky.util.Convert.asJsonString;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationTest {
    @Autowired
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RegistrationControllerV1 controller;

    @Test
    public void registrationUserTest() throws Exception{
        this.mockMvc.perform(post("/api/v1/registration")
        .contentType("application/json")
        .content(asJsonString(new RegistrationUserDto("NewUser",
                "Name",
                "LastName",
                "email@mail.com",
                "123"))))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/api/v1/login")
                .contentType("application/json")
                .content(asJsonString(new AuthenticationRequestDto("NewUser","123"))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void invalidRegistration() throws Exception{
        this.mockMvc.perform(post("/api/v1/registration")
                .contentType("application/json")
                .content(asJsonString(new RegistrationUserDto("pkyfen",
                        "Name",
                        "LastName",
                        "email@mail.com",
                        "123"))))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Error").value("Entity already exist"));
    }

}
