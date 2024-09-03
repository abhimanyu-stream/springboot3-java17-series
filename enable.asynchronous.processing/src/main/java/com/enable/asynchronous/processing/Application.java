package com.enable.asynchronous.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  // Enable async processing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
