package com.smarthealth.pharmacy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthealth.pharmacy.dto.RecetaCreateDto;
import com.smarthealth.pharmacy.dto.RecetaResponseDto;
import com.smarthealth.pharmacy.service.RecetaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recetas")
public class RecetaController {
	
	private final RecetaService service;

	@GetMapping
	public ResponseEntity<List<RecetaResponseDto>> listarRecetas(
			@RequestParam(required = false) String medicamento
	) {
		List<RecetaResponseDto> lista = service.listarRecetas(medicamento);

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecetaResponseDto> obtenerRecetaPorId(@PathVariable Long id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerRecetaPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<RecetaResponseDto> registrarReceta(@Valid @RequestBody RecetaCreateDto dto) {
		RecetaResponseDto creada = service.registrarReceta(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}

}
