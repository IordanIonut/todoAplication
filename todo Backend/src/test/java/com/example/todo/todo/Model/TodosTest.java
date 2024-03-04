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

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
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
public class TodosTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Todos> todos;

    public static List<Users> users;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp(){
        UsersTest.setUp();
        users = UsersTest.users;

        todos = Arrays.asList(
                Todos.builder().todo_id(1L).user_id(users.get(0)).date(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Todos.builder().todo_id(2L).user_id(users.get(1)).date(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Todos.builder().todo_id(3L).user_id(users.get(2)).date(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Todos.builder().todo_id(4L).user_id(users.get(3)).date(LocalDateTime.parse("2024-12-31T23:59:59")).build());
    }

    @Test
    public void testSaveTodos() throws Exception {
        mvc.perform(post("/api/todo/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todos.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.todo_id").value(todos.get(0).getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(todos.get(0).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.date").value(todos.get(0).getDate().toString()));

        mvc.perform(post("/api/todo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todos.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.todo_id").value(todos.get(1).getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(todos.get(1).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.date").value(todos.get(1).getDate().toString()));

        mvc.perform(post("/api/todo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todos.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.todo_id").value(todos.get(2).getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(todos.get(2).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.date").value(todos.get(2).getDate().toString()));

        mvc.perform(post("/api/todo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todos.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.todo_id").value(todos.get(3).getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(todos.get(3).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.date").value(todos.get(3).getDate().toString()));
    }

    @Test
    public void testGetAllTodos() throws Exception {
        mvc.perform(get("/api/todo/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetTodosById() throws Exception{
        mvc.perform(get("/api/todo/id?todo_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].todo_id").value(todos.get(0).getTodo_id().longValue()))
                .andExpect(jsonPath("$[0].user_id.user_id").value(todos.get(0).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$[0].date").value(todos.get(0).getDate().toString()));
    }

    @Test
    public void testDeleteTodos() throws Exception {
        mvc.perform(delete("/api/todo/id?todo_id=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateTodos() throws Exception{
        mvc.perform(put("/api/todo/update?todo_id=2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Todos.builder()
                                .todo_id(2L)
                                .user_id(users.get(2))
                                .date(LocalDateTime.parse("2024-12-22T23:59:59"))
                                .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.todo_id").value(2L))
                .andExpect(jsonPath("$.user_id.user_id").value(users.get(2).getUser_id().longValue()))
                .andExpect(jsonPath("$.date").value("2024-12-22T23:59:59"));
    }

    @Test
    public void testTodosUsersNull() throws Exception{
        result = mvc.perform(post("/api/todo/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Todos.builder()
                        .todo_id(2L)
                        .date(LocalDateTime.parse("2024-12-22T23:59:59"))
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Users"));
    }

    @Test
    public void testTodosDateNull() throws Exception{
        result = mvc.perform(post("/api/todo/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Todos.builder()
                                .todo_id(2L)
                                .user_id(users.get(0))
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Date"));
    }
}
