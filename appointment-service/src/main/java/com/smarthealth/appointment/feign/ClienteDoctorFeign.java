package com.smarthealth.appointment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smarthealth.appointment.config.FeignJwtConfig;

@FeignClient(name = "doctor-service", configuration = FeignJwtConfig.class)
public interface ClienteDoctorFeign {

	@GetMapping("/api/doctores/{id}")
	DoctorFeign obtenerDoctorPorId(@PathVariable Integer id);

}
