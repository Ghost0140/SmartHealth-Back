package com.smarthealth.reception.controller;

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
import com.smarthealth.reception.dto.RecepcionistaCreateDto;
import com.smarthealth.reception.dto.RecepcionistaResponseDto;
import com.smarthealth.reception.dto.RecepcionistaUpdateDto;
import com.smarthealth.reception.service.RecepcionistaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recepcionista")
public class RecepcionistaController {

	private final RecepcionistaService service;

	@GetMapping
	public ResponseEntity<List<RecepcionistaResponseDto>> listarRecepcionista(
			@RequestParam(required = false) Boolean activo
	) {
		List<RecepcionistaResponseDto> lista = service.listarRecepcionista(activo);

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecepcionistaResponseDto> obtenerRecepcionistaPorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerRecepcionistaPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<RecepcionistaResponseDto> registrarRecepcionista(@Valid @RequestBody RecepcionistaCreateDto dto) {
		RecepcionistaResponseDto creada = service.registrarRecepcionista(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RecepcionistaResponseDto> actualizarRecepcionista(
			@PathVariable Integer id, 
			@Valid @RequestBody RecepcionistaUpdateDto dto
	) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.actualizarRecepcionista(id, dto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PatchMapping("/reactivar/{id}")
	public ResponseEntity<Void> reactivarRecepcionista(@PathVariable Integer id) {
	    if (id <= 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    boolean ok = service.reactivarRecepcionista(id);

	    if (!ok) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> desactivarRecepcionista(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		boolean ok = service.desactivarRecepcionista(id);

		if (!ok) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}
}
