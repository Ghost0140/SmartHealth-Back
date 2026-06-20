package com.smarthealth.patient.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthealth.patient.dto.PacienteCreateDto;
import com.smarthealth.patient.dto.PacienteResponseDto;
import com.smarthealth.patient.dto.PacienteUpdateDto;
import com.smarthealth.patient.service.PacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pacientes")
public class PacienteController {
	
	private final PacienteService service;

	@GetMapping
	public ResponseEntity<List<PacienteResponseDto>> listarPacientes(
			@RequestParam(required = false) Boolean activo
	) {
		List<PacienteResponseDto> lista = service.listarPacientes(activo);

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PacienteResponseDto> obtenerPacientePorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerPacientePorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<PacienteResponseDto> registrarPaciente(@Valid @RequestBody PacienteCreateDto dto) {
		PacienteResponseDto creada = service.registrarPaciente(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PacienteResponseDto> actualizarPaciente(
			@PathVariable Integer id, 
			@Valid @RequestBody PacienteUpdateDto dto
	) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.actualizarPaciente(id, dto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PatchMapping("/reactivar/{id}")
	public ResponseEntity<Void> reactivarPaciente(@PathVariable Integer id) {
	    if (id <= 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    boolean ok = service.reactivarPaciente(id);

	    if (!ok) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> desactivarPaciente(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		boolean ok = service.desactivarPaciente(id);

		if (!ok) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}
