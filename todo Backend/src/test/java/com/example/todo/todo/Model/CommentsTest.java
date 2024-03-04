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
public class CommentsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Comments> comments;

    public static List<Todos> todos;

    public static List<Users> users;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setup(){
        TodosTest.setUp();
        todos = TodosTest.todos;
        users = TodosTest.users;

        comments = Arrays.asList(
                Comments.builder().comment_id(1L).todo_id(todos.get(0)).user_id(users.get(0)).text("asdsad11").created(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Comments.builder().comment_id(2L).todo_id(todos.get(1)).user_id(users.get(1)).text("asdsad12").created(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Comments.builder().comment_id(3L).todo_id(todos.get(2)).user_id(users.get(2)).text("asdsad13").created(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Comments.builder().comment_id(4L).todo_id(todos.get(3)).user_id(users.get(3)).text("asdsad14").created(LocalDateTime.parse("2024-12-31T23:59:59")).build());
    }

    @Test
    public void testSaveComments() throws Exception {
        mvc.perform(post("/api/comment/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comments.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment_id").value(comments.get(0).getComment_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(comments.get(0).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(comments.get(0).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.text").value(comments.get(0).getText()))
                .andExpect(jsonPath("$.created").value(comments.get(0).getCreated().toString()));

        mvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment_id").value(comments.get(1).getComment_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(comments.get(1).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(comments.get(1).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.text").value(comments.get(1).getText()))
                .andExpect(jsonPath("$.created").value(comments.get(1).getCreated().toString()));

        mvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment_id").value(comments.get(2).getComment_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(comments.get(2).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(comments.get(2).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.text").value(comments.get(2).getText()))
                .andExpect(jsonPath("$.created").value(comments.get(2).getCreated().toString()));

        mvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment_id").value(comments.get(3).getComment_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(comments.get(3).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.user_id.user_id").value(comments.get(3).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$.text").value(comments.get(3).getText()))
                .andExpect(jsonPath("$.created").value(comments.get(3).getCreated().toString()));
    }

    @Test
    public void testGetAllComments() throws Exception {
        mvc.perform(get("/api/comment/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetCommentsById() throws Exception{
        mvc.perform(get("/api/comment/id?comment_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].comment_id").value(comments.get(0).getComment_id().longValue()))
                .andExpect(jsonPath("$[0].todo_id.todo_id").value(comments.get(0).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$[0].user_id.user_id").value(comments.get(0).getUser_id().getUser_id().longValue()))
                .andExpect(jsonPath("$[0].text").value(comments.get(0).getText()))
                .andExpect(jsonPath("$[0].created").value(comments.get(0).getCreated().toString()));
    }

    @Test
    public void testDeleteComments() throws Exception{
        mvc.perform(delete("/api/comment/id?comment_id=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateComments() throws Exception{
        mvc.perform(put("/api/comment/update?comment_id=2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Comments.builder()
                        .comment_id(2L)
                        .todo_id(todos.get(1))
                        .user_id(users.get(1))
                        .text("asdsad123")
                        .created(LocalDateTime.parse("2024-12-31T23:59:59")).build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment_id").value(2L))
                .andExpect(jsonPath("$.todo_id.todo_id").value(todos.get(1)))
                .andExpect(jsonPath("$.user_id.user_id").value(users.get(1)))
                .andExpect(jsonPath("$.text").value("asdsad123"))
                .andExpect(jsonPath("$.created").value("2024-12-31T23:59:59"));
    }

    @Test
    public void testCommentsTodosNull() throws Exception{
        result = mvc.perform(post("/api/comment/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Comments.builder()
                        .comment_id(2L)
                        .user_id(users.get(1))
                        .text("asdsad123")
                        .created(LocalDateTime.parse("2024-12-31T23:59:59")).build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Todo"));
    }

    @Test
    public void testCommentsUsersNull() throws Exception{
        result = mvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Comments.builder()
                                .comment_id(2L)
                                .todo_id(todos.get(1))
                                .text("asdsad123")
                                .created(LocalDateTime.parse("2024-12-31T23:59:59")).build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Users"));
    }

    @Test
    public void testCommentsTextNull() throws Exception{
        result = mvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Comments.builder()
                                .comment_id(2L)
                                .todo_id(todos.get(1))
                                .user_id(users.get(1))
                                .created(LocalDateTime.parse("2024-12-31T23:59:59")).build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Text"));
    }

    @Test
    public void testCommentsCreatedNull() throws Exception{
        result = mvc.perform(post("/api/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Comments.builder()
                                .comment_id(2L)
                                .todo_id(todos.get(1))
                                .user_id(users.get(1))
                                .text("asdsad123")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Created"));
    }
}

