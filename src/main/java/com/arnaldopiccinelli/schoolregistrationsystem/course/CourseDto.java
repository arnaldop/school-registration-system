package com.arnaldopiccinelli.schoolregistrationsystem.course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@Schema(name = "Course")
public class CourseDto extends BaseDto {
	@Schema(description = "Course ID.", example = "1")
	Long id;

	@Schema(description = "Course Name.")
	@NotBlank(message = "Name cannot be blank.")
	@Size(min = 1,
			max = 100,
			message = "Name must be between {min} and {max} characters.")
	String name;

	@Schema(description = "Course Identifier.")
	@NotBlank(message = "Identifier cannot be blank.")
	@Size(min = 1,
			max = 10,
			message = "Identifier must be between {min} and {max} characters.")
	String identifier;

	@Schema(description = "Course Description.")
	@Size(min = 1,
			max = 300,
			message = "Description must be between {min} and {max} characters.")
	String description;

	@Schema(example = "Student count")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	int studentCount;
}
