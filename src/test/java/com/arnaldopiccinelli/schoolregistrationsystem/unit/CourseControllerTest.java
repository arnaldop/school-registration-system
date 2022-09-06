package com.arnaldopiccinelli.schoolregistrationsystem.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseController;
import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseService;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	@Test
	public void getAllTodos_returnsCorrectList() throws Exception {
//		// arrange
//		var todo = new Todo();
//		todo.setId(123123);
//		todo.setName("hello 23143");
//
//		when(todoService.findAll()).thenReturn(Arrays.asList(todo));
//
//		// act
//		mockMvc.perform(get("/api/todos"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].id", is(123123)))
//				.andExpect(jsonPath("$[0].name", is("hello 23143")));
	}
}