package com.smarthealth.pharmacy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smarthealth.pharmacy.config.FeignJwtConfig;

@FeignClient(name = "appointment-service", configuration = FeignJwtConfig.class)
public interface ClienteCitaFeign {

	@GetMapping("/api/citas/{id}")
	CitaFeign obtenerCitaPorId(@PathVariable Long id);
	
	@PatchMapping("/api/citas/atender/{id}")
	void atenderCita(@PathVariable Long id);

}
