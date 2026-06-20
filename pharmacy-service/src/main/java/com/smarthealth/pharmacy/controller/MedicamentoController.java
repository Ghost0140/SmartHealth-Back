package com.smarthealth.pharmacy.controller;

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

import com.smarthealth.pharmacy.dto.MedicamentoCreateDto;
import com.smarthealth.pharmacy.dto.MedicamentoResponseDto;
import com.smarthealth.pharmacy.dto.MedicamentoUpdateDto;
import com.smarthealth.pharmacy.service.MedicamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medicamentos")
public class MedicamentoController {
	
	private final MedicamentoService service;

	@GetMapping
	public ResponseEntity<List<MedicamentoResponseDto>> listarMedicamentos(
			@RequestParam(required = false) Boolean activo
	) {
		List<MedicamentoResponseDto> lista = service.listarMedicamentos(activo);

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicamentoResponseDto> obtenerMedicamentoPorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerMedicamentoPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<MedicamentoResponseDto> registrarMedicamento(@Valid @RequestBody MedicamentoCreateDto dto) {
		MedicamentoResponseDto creada = service.registrarMedicamento(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MedicamentoResponseDto> actualizarMedicamento(
			@PathVariable Integer id, 
			@Valid @RequestBody MedicamentoUpdateDto dto
	) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.actualizarMedicamento(id, dto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PatchMapping("/reactivar/{id}")
	public ResponseEntity<Void> reactivarMedicamento(@PathVariable Integer id) {
	    if (id <= 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    boolean ok = service.reactivarMedicamento(id);

	    if (!ok) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> desactivarMedicamento(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		boolean ok = service.desactivarMedicamento(id);

		if (!ok) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}
