package com.smarthealth.appointment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smarthealth.appointment.config.FeignJwtConfig;

@FeignClient(name = "patient-service", configuration = FeignJwtConfig.class)
public interface ClientePacienteFeign {
	
	@GetMapping("/api/pacientes/{id}")
	PacienteFeign obtenerPacientePorId(@PathVariable Integer id);

}
