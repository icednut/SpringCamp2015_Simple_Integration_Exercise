package com.springcamp.integration.exercise.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@ImportResource("/META-INF/integration-context.xml")
@PropertySource("classpath:application.properties")
public class SpringReceiveAppApplication {
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public static void main(String[] args) {
        SpringApplication.run(SpringReceiveAppApplication.class, args);
    }
}
