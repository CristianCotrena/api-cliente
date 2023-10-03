package com.api.cliente;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(title = "API Cliente", version = "1", description = "API de clientes da academia."))
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
