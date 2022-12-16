package com.study.trainingboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TrainingBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingBoardApplication.class, args);
	}

}
