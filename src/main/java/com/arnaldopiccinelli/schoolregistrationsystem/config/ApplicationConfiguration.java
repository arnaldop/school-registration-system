package com.arnaldopiccinelli.schoolregistrationsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties("school-registration")
@Data
@Slf4j
public class ApplicationConfiguration {
	private int maxStudentsPerCourse;
	private int maxCoursesPerStudent;
}
