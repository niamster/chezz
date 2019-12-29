package api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void testEmptyUsers() throws Exception {
    mockMvc
        .perform(get("/public/users"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$").isEmpty())
        .andDo(print());
  }

  @Test
  public void testCornerCases() throws Exception {
    mockMvc
        .perform(
            post("/public/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    objectMapper.writeValueAsBytes(
                        new UserController.SignUpRequest("", "u@u", ""))))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.status").value("fail"))
        .andExpect(jsonPath("$.token").isEmpty())
        .andDo(print());
  }

  @Test
  public void testSignup() throws Exception {
    String username = "user_0", password = "_password_", email = "user@org";
    mockMvc
        .perform(
            post("/public/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    objectMapper.writeValueAsBytes(
                        new UserController.SignInRequest(username, password))))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.status").value("fail"))
        .andExpect(jsonPath("$.token").isEmpty())
        .andDo(print());
    var result =
        mockMvc
            .perform(
                post("/public/signup")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(
                        objectMapper.writeValueAsBytes(
                            new UserController.SignUpRequest(username, email, password))))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.status").value("ok"))
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    var userToken = objectMapper.readValue(result, UserController.Status.class).token;
    mockMvc
        .perform(
            post("/public/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    objectMapper.writeValueAsBytes(
                        new UserController.SignUpRequest(username, email, password))))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.status").value("fail"))
        .andDo(print());
    mockMvc
        .perform(get("/protected/user/info").param("user_token", userToken))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.username").value(username))
        .andExpect(jsonPath("$.email").value(email))
        .andDo(print());
    mockMvc
        .perform(get("/protected/signout").param("user_token", userToken))
        .andExpect(status().isOk());
    mockMvc
        .perform(get("/protected/user/info").param("user_token", userToken))
        .andExpect(status().is4xxClientError())
        .andDo(print());
    mockMvc
        .perform(
            post("/public/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    objectMapper.writeValueAsBytes(
                        new UserController.SignInRequest(username, password))))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.status").value("ok"))
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andDo(print());
  }
}
