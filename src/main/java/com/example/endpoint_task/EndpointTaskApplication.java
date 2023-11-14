package com.example.endpoint_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EndpointTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(EndpointTaskApplication.class, args);
	}

}
