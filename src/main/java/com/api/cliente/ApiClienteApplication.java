package com.api.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClienteApplication.class, args);
	}

	@GetMapping("/olamundo")
	public String olaMundo() {
		return "OLA MUNDO DA API CLIENTE!";
	}
}
