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
public class TagsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Tags> tags;

    public static List<Todos> todos;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        TodosTest.setUp();
        todos = TodosTest.todos;

        tags = Arrays.asList(
                Tags.builder().tag_id(1L).todo_id(todos.get(0)).name("asdassa1").build(),
                Tags.builder().tag_id(2L).todo_id(todos.get(1)).name("asdassa2").build(),
                Tags.builder().tag_id(3L).todo_id(todos.get(2)).name("asdassa3").build(),
                Tags.builder().tag_id(4L).todo_id(todos.get(3)).name("asdassa4").build());
    }

    @Test
    public void testSaveTags() throws Exception{
        mvc.perform(post("/api/tags/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tags.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tag_id").value(tags.get(0).getTag_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(tags.get(0).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.name").value(tags.get(0).getName()));

        mvc.perform(post("/api/tags/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tags.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tag_id").value(tags.get(1).getTag_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(tags.get(1).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.name").value(tags.get(1).getName()));

        mvc.perform(post("/api/tags/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tags.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tag_id").value(tags.get(2).getTag_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(tags.get(2).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.name").value(tags.get(2).getName()));

        mvc.perform(post("/api/tags/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tags.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tag_id").value(tags.get(3).getTag_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(tags.get(3).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.name").value(tags.get(3).getName()));
    }

    @Test
    public void testGetAllTags() throws Exception {
        mvc.perform(get("/api/tags/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetTagsById() throws Exception{
        mvc.perform(get("/api/tags/id?tag_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tag_id").value(tags.get(0).getTag_id().longValue()))
                .andExpect(jsonPath("$[0].todo_id.todo_id").value(tags.get(0).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$[0].name").value(tags.get(0).getName()));
    }

    @Test
    public void testDeleteTags() throws Exception{
        mvc.perform(delete("/api/tags/id?tag_id=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateTags() throws Exception{
        mvc.perform(put("/api/tags/update?tag_id=2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Tags.builder()
                                .tag_id(2L)
                                .todo_id(todos.get(1))
                                .name("asdassa2")
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag_id").value(2L))
                .andExpect(jsonPath("$.todo_id.todo_id").value(todos.get(1).getTodo_id().longValue()))
                .andExpect(jsonPath("$.name").value("asdassa2"));
    }

    @Test
    public void testTagsTodosNull() throws Exception{
        result = mvc.perform(post("/api/tags/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Tags.builder()
                                .tag_id(1L)
                                .name("asdassa1")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Todo"));
    }

    @Test
    public void testTagsNameNull() throws Exception{
        result = mvc.perform(post("/api/tags/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Tags.builder()
                                .tag_id(1L)
                                .todo_id(todos.get(0))
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Name"));
    }

}
