package com.example.todo.todo.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class UsersTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Users> users;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp(){
        users = Arrays.asList(
                Users.builder().user_id(1L).name("asdasda1").email("asdadasd1").password("asdasdsad1").build(),
                Users.builder().user_id(2L).name("asdasda2").email("asdadasd2").password("asdasdsad2").build(),
                Users.builder().user_id(3L).name("asdasda3").email("asdadasd3").password("asdasdsad3").build(),
                Users.builder().user_id(4L).name("asdasda4").email("asdadasd4").password("asdasdsad4").build());
    }

    @Test
    public void testSaveUsers() throws Exception{
        mvc.perform(post("/api/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user_id").value(users.get(0).getUser_id().longValue()))
                .andExpect(jsonPath("$.name").value(users.get(0).getName()))
                .andExpect(jsonPath("$.email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$.password").value(users.get(0).getPassword()));

        mvc.perform(post("/api/users/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user_id").value(users.get(1).getUser_id().longValue()))
                .andExpect(jsonPath("$.name").value(users.get(1).getName()))
                .andExpect(jsonPath("$.email").value(users.get(1).getEmail()))
                .andExpect(jsonPath("$.password").value(users.get(1).getPassword()));

        mvc.perform(post("/api/users/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user_id").value(users.get(2).getUser_id().longValue()))
                .andExpect(jsonPath("$.name").value(users.get(2).getName()))
                .andExpect(jsonPath("$.email").value(users.get(2).getEmail()))
                .andExpect(jsonPath("$.password").value(users.get(2).getPassword()));

        mvc.perform(post("/api/users/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user_id").value(users.get(3).getUser_id().longValue()))
                .andExpect(jsonPath("$.name").value(users.get(3).getName()))
                .andExpect(jsonPath("$.email").value(users.get(3).getEmail()))
                .andExpect(jsonPath("$.password").value(users.get(3).getPassword()));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mvc.perform(get("/api/users/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetUsersById() throws Exception{
        mvc.perform(get("/api/users/id?user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user_id").value(users.get(0).getUser_id().longValue()))
                .andExpect(jsonPath("$[0].name").value(users.get(0).getName()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].password").value(users.get(0).getPassword()));
    }

    @Test
    public void testDeleteUsers() throws Exception{
        mvc.perform(delete("/api/users/id?user_id=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateUsers() throws Exception{
        mvc.perform(put("/api/users/update?user_id=4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Users.builder()
                        .user_id(4L)
                        .name("asdasda44")
                        .email("asdadasd44")
                        .password("asdasdsad44")
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(4L))
                .andExpect(jsonPath("$.name").value("asdasda44"))
                .andExpect(jsonPath("$.email").value("asdadasd44"))
                .andExpect(jsonPath("$.password").value("asdasdsad44"));
    }

    @Test
    public void testUsersNameNull() throws Exception{
        result = mvc.perform(post("/api/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Users.builder()
                        .email("asdadasd44")
                        .password("asdasdsad44")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Name"));
    }

    @Test
    public void testUsersEmailNull() throws Exception{
        result = mvc.perform(post("/api/users/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .name("asdasda44")
                                .password("asdasdsad44")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Email"));
    }

    @Test
    public void testUsersPasswordNull() throws Exception{
        result = mvc.perform(post("/api/users/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .name("asdasda44")
                                .email("asdadasd44")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Password"));
    }
}
