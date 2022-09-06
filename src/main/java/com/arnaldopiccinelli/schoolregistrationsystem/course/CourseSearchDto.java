package com.arnaldopiccinelli.schoolregistrationsystem.course;

import java.util.List;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseSearchDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@Schema(name = "Course Search")
public class CourseSearchDto extends BaseSearchDto {
	List<Long> ids;
	List<Long> studentIds;
	Boolean empty;
}
