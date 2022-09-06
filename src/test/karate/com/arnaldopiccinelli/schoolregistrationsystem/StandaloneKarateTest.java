package com.arnaldopiccinelli.schoolregistrationsystem;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = {SchoolRegistrationSystemApplication.class})
@ActiveProfiles("test")
@Testcontainers
@Slf4j
public class StandaloneKarateTest {
	@LocalServerPort
	protected int randomServerPort;

	@Test
	void runKarateTests() {
		final Results results =
				Runner.path("classpath:com/arnaldopiccinelli/schoolregistrationsystem")
						.tags("~@ignore", "~@local-only")
						.systemProperty("server.port", Integer.toString(randomServerPort))
						.systemProperty("inMaven", "true")
						.parallel(5);

		assertThat(results.getFeaturesTotal())
				.as("Features did not run.")
				.isGreaterThan(0);
		assertThat(results.getScenariosTotal())
				.as("Scenarios did not run.")
				.isGreaterThan(0);
		assertThat(results.getFailCount())
				.as(results.getErrorMessages())
				.isEqualTo(0);
	}
}
