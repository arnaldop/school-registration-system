package com.arnaldopiccinelli.schoolregistrationsystem.student;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseController;
import com.arnaldopiccinelli.schoolregistrationsystem.course.Course;
import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseDto;
import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseSearchDto;
import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("students")
@Tag(name = "Students")
public class StudentController extends BaseController<Student, StudentDto, StudentSearchDto> {
	@Getter
	private final StudentService service;
}
