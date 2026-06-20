package com.smarthealth.appointment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthealth.appointment.dto.CitaCreateDto;
import com.smarthealth.appointment.dto.CitaResponseDto;
import com.smarthealth.appointment.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/citas")
public class CitaController {
	
	private final CitaService service;
	
	@GetMapping
	public ResponseEntity<List<CitaResponseDto>> listarCitas(
	        @RequestParam(required = false) String estado
	) {
	    List<CitaResponseDto> lista = service.listarCitas(estado);

	    if (lista.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }

	    return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CitaResponseDto> obtenerCitaPorId(@PathVariable Long id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerCitaPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<CitaResponseDto> registrarCita(@Valid @RequestBody CitaCreateDto dto) {
		CitaResponseDto creada = service.registrarCita(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}

	@PatchMapping("/atender/{id}")
	public ResponseEntity<Void> atenderCita(@PathVariable Long id) {
	    if (id <= 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    boolean ok = service.atenderCita(id);

	    if (!ok) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/cancelar/{id}")
	public ResponseEntity<Void> cancelarCita(@PathVariable Long id) {
	    if (id <= 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    boolean ok = service.cancelarCita(id);

	    if (!ok) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.noContent().build();
	}

}
