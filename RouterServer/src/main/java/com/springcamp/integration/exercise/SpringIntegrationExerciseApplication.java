package com.springcamp.integration.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class SpringIntegrationExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationExerciseApplication.class, args);
	}
}
