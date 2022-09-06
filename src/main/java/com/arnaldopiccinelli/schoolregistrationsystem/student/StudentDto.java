package com.arnaldopiccinelli.schoolregistrationsystem.student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@Schema(name = "Student")
public class StudentDto extends BaseDto {
	@Schema(description = "Student ID.", example = "1")
	Long id;

	@Schema(description = "Student Identifier.")
	@NotBlank(message = "Identifier cannot be blank.")
	@Size(min = 1,
			max = 10,
			message = "Identifier must be between {min} and {max} characters.")
	String identifier;

	@Schema(description = "Student First Name.")
	@NotBlank(message = "First Name cannot be blank.")
	@Size(min = 1,
			max = 50,
			message = "First Name must be between {min} and {max} characters.")
	String firstName;

	@Schema(description = "Student Last Name.")
	@NotBlank(message = "Last Name cannot be blank.")
	@Size(min = 1,
			max = 60,
			message = "Last Name must be between {min} and {max} characters.")
	String lastName;

	@Schema(example = "Course count")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	int courseCount;
}
