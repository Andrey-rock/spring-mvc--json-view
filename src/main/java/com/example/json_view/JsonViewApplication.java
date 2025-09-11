package com.example.json_view;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class JsonViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonViewApplication.class, args);
	}

}
