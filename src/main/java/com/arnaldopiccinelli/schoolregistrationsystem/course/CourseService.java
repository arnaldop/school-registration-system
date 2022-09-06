package com.arnaldopiccinelli.schoolregistrationsystem.course;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseService;
import com.arnaldopiccinelli.schoolregistrationsystem.config.ApplicationConfiguration;
import com.arnaldopiccinelli.schoolregistrationsystem.student.Student;
import com.arnaldopiccinelli.schoolregistrationsystem.student.StudentService;
import com.arnaldopiccinelli.schoolregistrationsystem.student.Student_;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService extends BaseService<Course, CourseDto, CourseSearchDto> {
	@Getter
	private final CourseRepository repository;
	@Getter
	private final CourseMapper mapper;
	private final StudentService studentService;
	private final ApplicationConfiguration applicationConfiguration;

	@Override
	public Specification<Course> getSearchSpecification(
			@NonNull final CourseSearchDto searchDto) {
		return (root, query, criteriaBuilder) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(root.get(Course_.ID).isNotNull());

			if (CollectionUtils.isNotEmpty(searchDto.getIds())) {
				predicates.add(root.get(Course_.ID).in(searchDto.getIds()));
			}

			if (CollectionUtils.isNotEmpty(searchDto.getStudentIds())) {
				predicates.add(root.join(Course_.STUDENTS).get(Student_.ID)
						.in(searchDto.getStudentIds()));
			}

			if (Boolean.TRUE.equals(searchDto.getEmpty())) {
				predicates.add(criteriaBuilder.isEmpty(root.get(Course_.STUDENTS)));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	// Uniqueness determined by course identifier OR name.
	@Override
	public Specification<Course> getUniqueSpecification(
			@NonNull final CourseDto dto) {
		return (root, query, criteriaBuilder) -> {
			final List<Predicate> predicates = new ArrayList<>();

			predicates.add(criteriaBuilder.equal(root.get(Course_.IDENTIFIER),
					dto.getIdentifier()));
			predicates.add(criteriaBuilder.equal(root.get(Course_.NAME), dto.getName()));

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		};
	}

	void addStudent(@PathVariable final long id, @PathVariable final long studentId) {
		final Course course = read(id);

		if (course == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found.");
		}

		final Student student = studentService.read(studentId);

		if (student == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.");
		}

		if (course.getStudents()
				.size() >= applicationConfiguration.getMaxStudentsPerCourse()) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
					"Course full.");
		}

		if (student.getCourses()
				.size() >= applicationConfiguration.getMaxCoursesPerStudent()) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
					"Student has full course load.");
		}

		course.addStudent(student);
		update(course);
	}
}
