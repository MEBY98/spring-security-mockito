package com.example.meby98.TestWithMockito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TestWithMockitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestWithMockitoApplication.class, args);
	}

}
