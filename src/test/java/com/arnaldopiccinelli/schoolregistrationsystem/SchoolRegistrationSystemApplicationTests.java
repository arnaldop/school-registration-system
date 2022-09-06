package com.arnaldopiccinelli.schoolregistrationsystem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseDto;
import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseSearchDto;
import com.arnaldopiccinelli.schoolregistrationsystem.student.StudentDto;
import com.arnaldopiccinelli.schoolregistrationsystem.student.StudentSearchDto;
import com.github.javafaker.Faker;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@Slf4j
public class SchoolRegistrationSystemApplicationTests {
	private static final Faker FAKER = new Faker(new Locale("en-US"));

	@Autowired
	public TestRestTemplate restTemplate;

	@Test
	public void allTests() {
		assertThat(getAllCourses()).isEmpty();

		final int totalNumberOfCourses = 10;
		for (int i = 0; i < totalNumberOfCourses; i++) {
			createCourse(i);
		}
		assertThat(getAllCourses()).hasSize(totalNumberOfCourses);

		final int totalNumberOfStudents = 10;
		for (int i = 0; i < totalNumberOfStudents; i++) {
			createStudent();
		}
		assertThat(getAllStudents()).hasSize(totalNumberOfStudents);

		registerStudentInCourse(1, 1);
		registerStudentInCourse(1, 2);
		registerStudentInCourse(1, 3);
		registerStudentInCourse(1, 4);
		registerStudentInCourse(1, 5);
		registerStudentInCourse(1, 6, HttpStatus.EXPECTATION_FAILED);

		registerStudentInCourse(2, 1);
		registerStudentInCourse(3, 1);
		registerStudentInCourse(4, 1, HttpStatus.EXPECTATION_FAILED);

		findStudentsInCourse(1L, 5);
		findCoursesWithStudent(1L, 3);
		findCoursesWithoutStudents(7);
		findStudentsWithoutCourses(5);
	}

	private List<CourseDto> getAllCourses() {
		return restTemplate.getForObject("/api/courses", CourseList.class).getContent();
	}

	private List<StudentDto> getAllStudents() {
		return restTemplate.getForObject("/api/students", StudentList.class).getContent();
	}

	private CourseDto createCourse(final int courseNumber) {
		final String code = FAKER.number().digits(5);
		final CourseDto newCourse = new CourseDto(null,
				FAKER.book().title() + " - " + code,
				"SRSC-" + code, "Course #" + courseNumber, 0);
		log.info("newCourse = {}", newCourse);

		final ResponseEntity<CourseDto> createResponse = restTemplate.exchange(
				"/api/courses", HttpMethod.POST,
				new HttpEntity<>(newCourse), CourseDto.class);
		log.info("createResponse = {}", createResponse);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		final CourseDto createdCourse = createResponse.getBody();
		assertThat(createdCourse.getId()).isNotNull();
		log.info("createdCourse = {}", createdCourse);

		return createdCourse;
	}

	private StudentDto createStudent() {
		final StudentDto newStudent = new StudentDto(null,
				"SRSS-" + FAKER.number().digits(5), FAKER.name().firstName(),
				FAKER.name().lastName(), 0);
		log.info("newStudent = {}", newStudent);

		final ResponseEntity<StudentDto> createResponse = restTemplate.exchange(
				"/api/students", HttpMethod.POST,
				new HttpEntity<>(newStudent), StudentDto.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		final StudentDto createdStudent = createResponse.getBody();
		assertThat(createdStudent.getId()).isNotNull();
		log.info("createdStudent = {}", createdStudent);

		return createdStudent;
	}

	private void registerStudentInCourse(final int courseId, final int studentId) {
		registerStudentInCourse(courseId, studentId, HttpStatus.OK);
	}

	private void registerStudentInCourse(final int courseId, final int studentId,
										 final HttpStatus expectedHttpStatus) {
		final ResponseEntity<Void> response = restTemplate.exchange(
				"/api/courses/{courseId}/student/{studentId}", HttpMethod.PUT, null,
				Void.class, Map.of("courseId", courseId, "studentId", studentId));
		assertThat(response.getStatusCode()).isEqualTo(expectedHttpStatus);
	}

	private void findStudentsInCourse(final long courseId, final int studentCount) {
		final ResponseEntity<StudentList> searchResponse = restTemplate.exchange(
				"/api/students/search", HttpMethod.POST,
				new HttpEntity<>(new StudentSearchDto(null, List.of(courseId), null)),
				StudentList.class);
		log.info("searchResponse = {}", searchResponse);
		assertThat(searchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		final StudentList studentList = searchResponse.getBody();
		assertThat(studentList.totalElements).isEqualTo(studentCount);
	}

	private void findCoursesWithStudent(final long studentId, final int courseCount) {
		final ResponseEntity<CourseList> searchResponse = restTemplate.exchange(
				"/api/courses/search", HttpMethod.POST,
				new HttpEntity<>(new CourseSearchDto(null, List.of(studentId), null)),
				CourseList.class);
		log.info("searchResponse = {}", searchResponse);
		assertThat(searchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		final CourseList courseList = searchResponse.getBody();
		assertThat(courseList.totalElements).isEqualTo(courseCount);
	}

	private void findCoursesWithoutStudents(final int courseCount) {
		final ResponseEntity<CourseList> searchResponse = restTemplate.exchange(
				"/api/courses/search", HttpMethod.POST,
				new HttpEntity<>(new CourseSearchDto(null, null, true)),
				CourseList.class);
		log.info("searchResponse = {}", searchResponse);
		assertThat(searchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		final CourseList courseList = searchResponse.getBody();
		assertThat(courseList.totalElements).isEqualTo(courseCount);
	}

	private void findStudentsWithoutCourses(final int studentCount) {
		final ResponseEntity<StudentList> searchResponse = restTemplate.exchange(
				"/api/students/search", HttpMethod.POST,
				new HttpEntity<>(new StudentSearchDto(null, null, true)),
				StudentList.class);
		log.info("searchResponse = {}", searchResponse);
		assertThat(searchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		final StudentList studentList = searchResponse.getBody();
		assertThat(studentList.totalElements).isEqualTo(studentCount);
	}

	@Data
	static class CourseList {
		private List<CourseDto> content = new ArrayList<>();
		private int numberOfElements;
		private int totalElements;
	}

	@Data
	static class StudentList {
		private List<StudentDto> content = new ArrayList<>();
		private int numberOfElements;
		private int totalElements;
	}
}
