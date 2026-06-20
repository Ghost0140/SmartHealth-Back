package com.smarthealth.auth.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthealth.auth.dto.RolResponseDto;
import com.smarthealth.auth.service.RolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RolController {
	
	private final RolService service;
	
	@GetMapping
	public ResponseEntity<List<RolResponseDto>> listarRoles(
			@RequestParam(required = false) Boolean activo
	) {
		List<RolResponseDto> lista = service.listaRoles(activo);

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RolResponseDto> obtenerRolPorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerRolPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
