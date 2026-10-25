package com.smarthealth.doctor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.smarthealth.doctor.dto.UsuarioDoctorCreateDto;

@FeignClient(
		name = "auth-service",
		url = "${smarthealth.services.auth-url:http://localhost:8086}",
		configuration = AuthFeignConfig.class)
public interface AuthUserClient {

	@PostMapping("/api/usuarios/doctores")
	void crearUsuarioDoctor(@RequestBody UsuarioDoctorCreateDto dto);

	@PutMapping("/api/usuarios/doctores/{idDoctor}/correo")
	void actualizarCorreoDoctor(
			@PathVariable Integer idDoctor,
			@RequestParam String correo);

	@PatchMapping("/api/usuarios/doctores/{idDoctor}/estado")
	void actualizarEstadoDoctor(
			@PathVariable Integer idDoctor,
			@RequestParam boolean activo);
}
