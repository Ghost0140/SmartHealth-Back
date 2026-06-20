package com.smarthealth.doctor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthealth.doctor.dto.EspecialidadResponseDto;
import com.smarthealth.doctor.service.EspecialidadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/especialidades")
public class EspecialidadController {
	
	private final EspecialidadService service;

	@GetMapping
	public ResponseEntity<List<EspecialidadResponseDto>> listarEspecialidades(
			@RequestParam(required = false) Boolean activo
	) {
		List<EspecialidadResponseDto> lista = service.listarEspecialidades(activo);

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EspecialidadResponseDto> obtenerEspecialidadPorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerEspecialidadPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
