package com.smarthealth.citas_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pacientes-service")
public interface ClientePacienteFeign {
	
	@GetMapping("/api/pacientes/{id}")
	PacienteFeign obtenerPacientePorId(@PathVariable Integer id);

}
