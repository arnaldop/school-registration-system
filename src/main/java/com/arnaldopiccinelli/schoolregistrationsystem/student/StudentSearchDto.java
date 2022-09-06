package com.arnaldopiccinelli.schoolregistrationsystem.student;

import java.util.List;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseSearchDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@Schema(name = "Student Search")
public class StudentSearchDto extends BaseSearchDto {
	List<Long> ids;
	List<Long> courseIds;
	Boolean empty;
}
