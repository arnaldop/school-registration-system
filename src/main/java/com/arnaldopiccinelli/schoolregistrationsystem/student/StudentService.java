package com.arnaldopiccinelli.schoolregistrationsystem.student;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseService;
import com.arnaldopiccinelli.schoolregistrationsystem.course.Course_;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService extends BaseService<Student, StudentDto, StudentSearchDto> {
	@Getter
	private final StudentRepository repository;
	@Getter
	private final StudentMapper mapper;

	@Override
	public Specification<Student> getSearchSpecification(
			@NonNull final StudentSearchDto searchDto) {
		return (root, query, criteriaBuilder) -> {
			final List<Predicate> predicates = new ArrayList<>();

			if (CollectionUtils.isNotEmpty(searchDto.getIds())) {
				predicates.add(root.get(Student_.ID).in(searchDto.getIds()));
			}

			if (CollectionUtils.isNotEmpty(searchDto.getCourseIds())) {
				predicates.add(root.join(Student_.COURSES).get(Course_.ID)
						.in(searchDto.getCourseIds()));
			}

			if (Boolean.TRUE.equals(searchDto.getEmpty())) {
				predicates.add(criteriaBuilder.isEmpty(root.get(Student_.COURSES)));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public Specification<Student> getUniqueSpecification(
			@NonNull final StudentDto dto) {
		return (root, query, criteriaBuilder) -> {
			final List<Predicate> predicates = new ArrayList<>();

			predicates.add(criteriaBuilder.equal(root.get(Course_.IDENTIFIER),
					dto.getIdentifier()));

			predicates.add(
					criteriaBuilder.and(
							criteriaBuilder.equal(root.get(Student_.FIRST_NAME),
									dto.getFirstName()),
							criteriaBuilder.equal(root.get(Student_.LAST_NAME),
									dto.getLastName())));

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		};
	}
}
