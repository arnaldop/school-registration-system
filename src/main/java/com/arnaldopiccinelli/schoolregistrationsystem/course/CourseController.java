package com.arnaldopiccinelli.schoolregistrationsystem.course;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("courses")
@Tag(name = "Courses")
public class CourseController extends BaseController<Course, CourseDto, CourseSearchDto> {
	@Getter
	private final CourseService service;

	@PutMapping("{id}/student/{studentId}")
	public void addStudent(@PathVariable final long id,
						   @PathVariable final long studentId) {
		service.addStudent(id, studentId);
	}
}
