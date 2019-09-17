package ru.mytracky;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mytracky.controller.AuthenticationRestControllerV1;
import ru.mytracky.dto.AuthenticationRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AuthenticationRestControllerV1 controller;

	@Test
	public void successfulLoginTest() throws Exception{
		this.mockMvc.perform(post("/api/v1/login")
				.contentType("application/json")
				.content(asJsonString(new AuthenticationRequestDto("pkyfen","test"))))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("pkyfen"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
	}

    @Test
    public void wrongPasswordLoginTest() throws Exception{
        this.mockMvc.perform(post("/api/v1/login")
                .contentType("application/json")
                .content(asJsonString(new AuthenticationRequestDto("pkyfen","wrongPassword"))))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("invalid password"));
    }

    @Test
    public void wrongUsernameLoginTest() throws Exception{
        this.mockMvc.perform(post("/api/v1/login")
                .contentType("application/json")
                .content(asJsonString(new AuthenticationRequestDto("wrongName","wrongPassword"))))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User not registered"));
    }

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
