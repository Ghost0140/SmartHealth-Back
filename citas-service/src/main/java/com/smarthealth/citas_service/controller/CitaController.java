package com.smarthealth.citas_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarthealth.citas_service.dto.CitaCreateDto;
import com.smarthealth.citas_service.dto.CitaResponseDto;
import com.smarthealth.citas_service.dto.CitaUpdateDto;
import com.smarthealth.citas_service.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/citas")
public class CitaController {

	private final CitaService service;

	@GetMapping
	public ResponseEntity<List<CitaResponseDto>> listarCitas() {
		List<CitaResponseDto> lista = service.listarCitas();

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CitaResponseDto> obtenerCitaPorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerCitaPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<CitaResponseDto> crearCita(@Valid @RequestBody CitaCreateDto dto) {
		CitaResponseDto creada = service.crearCita(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CitaResponseDto> actualizarCita(
			@PathVariable Integer id, 
			@Valid @RequestBody CitaUpdateDto dto
	) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.actualizarCita(id, dto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> desactivarCita(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		boolean eliminado = service.desactivarCita(id);

		if (!eliminado) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}
