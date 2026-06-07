package com.smarthealth.citas_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctores-service")
public interface ClienteDoctorFeign {
	
	@GetMapping("/api/doctores/{id}")
	DoctorFeign obtenerDoctorPorId(@PathVariable Integer id);

}
