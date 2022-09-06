package com.arnaldopiccinelli.schoolregistrationsystem.student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseMapper;
import com.arnaldopiccinelli.schoolregistrationsystem.course.Course;
import com.arnaldopiccinelli.schoolregistrationsystem.course.CourseDto;

import lombok.NonNull;

@Mapper(componentModel = "spring")
public interface StudentMapper extends BaseMapper<Student, StudentDto> {
	@Mapping(target = "courses", ignore = true)
	Student toEntity(@NonNull final StudentDto dto);

	Student toEntity(@NonNull final StudentDto dto,
					@MappingTarget @NonNull final Student course);

	@Mapping(target = "courseCount", expression = "java(entity.getCourses().size())")
	StudentDto toDto(@NonNull final Student entity);
}
