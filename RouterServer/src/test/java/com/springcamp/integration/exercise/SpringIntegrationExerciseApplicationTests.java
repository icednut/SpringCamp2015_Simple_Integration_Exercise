package com.springcamp.integration.exercise;

import com.springcamp.integration.exercise.SpringIntegrationExerciseApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringIntegrationExerciseApplication.class)
@WebAppConfiguration
public class SpringIntegrationExerciseApplicationTests {

	@Test
	public void contextLoads() {
	}

}
