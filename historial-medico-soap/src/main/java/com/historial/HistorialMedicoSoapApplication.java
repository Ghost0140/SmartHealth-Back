package com.historial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HistorialMedicoSoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistorialMedicoSoapApplication.class, args);
	}

}
