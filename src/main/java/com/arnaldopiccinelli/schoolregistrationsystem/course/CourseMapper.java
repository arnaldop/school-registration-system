package com.arnaldopiccinelli.schoolregistrationsystem.course;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseMapper;

import lombok.NonNull;

@Mapper(componentModel = "spring")
public interface CourseMapper extends BaseMapper<Course, CourseDto> {
	@Mapping(target = "students", ignore = true)
	Course toEntity(@NonNull final CourseDto dto);

	Course toEntity(@NonNull final CourseDto dto,
					@MappingTarget @NonNull final Course course);

	@Mapping(target = "studentCount", expression = "java(entity.getStudents().size())")
	CourseDto toDto(@NonNull final Course entity);
}
