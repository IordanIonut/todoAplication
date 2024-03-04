package com.example.todo.todo;

import com.example.todo.todo.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
class TodoApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void runUsers() throws Exception {
		UsersTest usersTest = new UsersTest();
		usersTest.setMvc(mvc, objectMapper);
		usersTest.setUp();

		usersTest.testSaveUsers();
		usersTest.testGetAllUsers();
		usersTest.testGetUsersById();
		usersTest.testDeleteUsers();
		usersTest.testUpdateUsers();
		usersTest.testUsersEmailNull();
		usersTest.testUsersNameNull();
		usersTest.testUsersPasswordNull();
	}

	@Test
	void runTodos() throws Exception{
		TodosTest todosTest = new TodosTest();
		todosTest.setMvc(mvc, objectMapper);
		todosTest.setUp();

		todosTest.testSaveTodos();
		todosTest.testGetAllTodos();
		todosTest.testGetTodosById();
		todosTest.testDeleteTodos();
		todosTest.testUpdateTodos();
		todosTest.testTodosDateNull();
		todosTest.testTodosUsersNull();
	}

	@Test
	void runTags() throws Exception{
		TagsTest tagsTest = new TagsTest();
		tagsTest.setMvc(mvc, objectMapper);
		tagsTest.setUp();

		tagsTest.testSaveTags();
		tagsTest.testUpdateTags();
		tagsTest.testGetTagsById();
		tagsTest.testGetAllTags();
		tagsTest.testDeleteTags();
		tagsTest.testTagsNameNull();
		tagsTest.testTagsTodosNull();
	}

	@Test
	void runtComments() throws Exception{
		CommentsTest commentsTest = new CommentsTest();
		commentsTest.setMvc(mvc, objectMapper);
		commentsTest.setup();

		commentsTest.testSaveComments();
		commentsTest.testGetAllComments();
		commentsTest.testGetCommentsById();
		commentsTest.testDeleteComments();
		commentsTest.testDeleteComments();
		commentsTest.testCommentsCreatedNull();
		commentsTest.testCommentsTodosNull();
		commentsTest.testCommentsTextNull();
		commentsTest.testCommentsUsersNull();
	}

	@Test
	void runLists() throws Exception{
		ListsTest listsTest = new ListsTest();
		listsTest.setMvc(mvc, objectMapper);
		listsTest.setUp();

		listsTest.testSaveLists();
		listsTest.testGetAllLists();
		listsTest.testGetListsById();
		listsTest.testDeleteLists();
		listsTest.testUpdateLists();
		listsTest.testListsPriorityNull();
		listsTest.testListsTodosNull();
		listsTest.testListsTitleNull();
		listsTest.testListsStatusNull();
	}
}
