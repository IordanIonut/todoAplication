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

import javax.swing.plaf.PanelUI;
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
public class ListsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Todos> todos;

    public static List<Lists> lists;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setUp(){
        TodosTest.setUp();
        todos = TodosTest.todos;

        lists = Arrays.asList(
                Lists.builder().list_id(1L).todo_id(todos.get(0)).title("asd1").priority("asd1").status("asd1").build(),
                Lists.builder().list_id(2L).todo_id(todos.get(1)).title("asd2").priority("asd2").status("asd2").build(),
                Lists.builder().list_id(3L).todo_id(todos.get(2)).title("asd3").priority("asd3").status("asd3").build(),
                Lists.builder().list_id(4L).todo_id(todos.get(3)).title("asd4").priority("asd4").status("asd4").build());
    }

    @Test
    public void testSaveLists() throws Exception{
        mvc.perform(post("/api/list/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lists.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.list_id").value(lists.get(0).getList_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(lists.get(0).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.title").value(lists.get(0).getTitle()))
                .andExpect(jsonPath("$.priority").value(lists.get(0).getPriority()))
                .andExpect(jsonPath("$.status").value(lists.get(0).getStatus()));

        mvc.perform(post("/api/list/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lists.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.list_id").value(lists.get(1).getList_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(lists.get(1).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.title").value(lists.get(1).getTitle()))
                .andExpect(jsonPath("$.priority").value(lists.get(1).getPriority()))
                .andExpect(jsonPath("$.status").value(lists.get(1).getStatus()));

        mvc.perform(post("/api/list/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lists.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.list_id").value(lists.get(2).getList_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(lists.get(2).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.title").value(lists.get(2).getTitle()))
                .andExpect(jsonPath("$.priority").value(lists.get(2).getPriority()))
                .andExpect(jsonPath("$.status").value(lists.get(2).getStatus()));

        mvc.perform(post("/api/list/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lists.get(3))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.list_id").value(lists.get(3).getList_id().longValue()))
                .andExpect(jsonPath("$.todo_id.todo_id").value(lists.get(3).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$.title").value(lists.get(3).getTitle()))
                .andExpect(jsonPath("$.priority").value(lists.get(3).getPriority()))
                .andExpect(jsonPath("$.status").value(lists.get(3).getStatus()));
    }

    @Test
    public void testGetAllLists() throws Exception {
        mvc.perform(get("/api/list/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void testGetListsById() throws Exception{
        mvc.perform(get("/api/list/id?list_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].list_id").value(lists.get(0).getList_id().longValue()))
                .andExpect(jsonPath("$[0].todo_id.todo_id").value(lists.get(0).getTodo_id().getTodo_id().longValue()))
                .andExpect(jsonPath("$[0].title").value(lists.get(0).getTitle()))
                .andExpect(jsonPath("$[0].priority").value(lists.get(0).getPriority()))
                .andExpect(jsonPath("$[0].status").value(lists.get(0).getStatus()));
    }

    @Test
    public void testDeleteLists() throws Exception{
        mvc.perform(delete("/api/list/id?list_id=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateLists() throws Exception{
        mvc.perform(put("/api/list/update?list_id=2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Lists.builder()
                        .list_id(2L)
                        .todo_id(todos.get(2))
                        .title("asd22")
                        .priority("asd22")
                        .status("asd22")
                        .build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list_id").value(3L))
                .andExpect(jsonPath("$.todo_id.todo_id").value(todos.get(2).getTodo_id().longValue()))
                .andExpect(jsonPath("$.title").value("asd22"))
                .andExpect(jsonPath("$.priority").value("asd22"))
                .andExpect(jsonPath("$.status").value("asd22"));
    }

    @Test
    public void testListsTodosNull() throws Exception{
        result = mvc.perform(post("/api/list/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Lists.builder()
                        .list_id(2L)
                        .title("asd22")
                        .priority("asd22")
                        .status("asd22")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Todo"));
    }

    @Test
    public void testListsTitleNull() throws Exception{
        result = mvc.perform(post("/api/list/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Lists.builder()
                                .list_id(2L)
                                .todo_id(todos.get(1))
                                .priority("asd22")
                                .status("asd22")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Title"));
    }

    @Test
    public void testListsPriorityNull() throws Exception{
        result = mvc.perform(post("/api/list/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Lists.builder()
                                .list_id(2L)
                                .todo_id(todos.get(1))
                                .title("asd22")
                                .status("asd22")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Priority"));
    }

    @Test
    public void testListsStatusNull() throws Exception{
        result = mvc.perform(post("/api/list/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Lists.builder()
                                .list_id(2L)
                                .todo_id(todos.get(1))
                                .title("asd22")
                                .priority("asd22")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Todo"));
    }
}
