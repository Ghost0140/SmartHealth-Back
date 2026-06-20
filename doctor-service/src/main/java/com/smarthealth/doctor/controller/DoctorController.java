package com.smarthealth.doctor.controller;

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

import com.smarthealth.doctor.dto.DoctorCreateDto;
import com.smarthealth.doctor.dto.DoctorResponseDto;
import com.smarthealth.doctor.dto.DoctorUpdateDto;
import com.smarthealth.doctor.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctores")
public class DoctorController {

	private final DoctorService service;

	@GetMapping
	public ResponseEntity<List<DoctorResponseDto>> listarDoctores(
	        @RequestParam(required = false) Boolean activo
	) {
	    List<DoctorResponseDto> lista = service.listarDoctores(activo);

	    if (lista.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }

	    return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DoctorResponseDto> obtenerDoctorPorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerDoctorPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<DoctorResponseDto> registrarDoctor(@Valid @RequestBody DoctorCreateDto dto) {
		DoctorResponseDto creado = service.registrarDoctor(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DoctorResponseDto> actualizarDoctor(
			@PathVariable Integer id, 
			@Valid @RequestBody DoctorUpdateDto dto
	) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.actualizarDoctor(id, dto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PatchMapping("/reactivar/{id}")
	public ResponseEntity<Void> reactivarDoctor(@PathVariable Integer id) {
	    if (id <= 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    boolean ok = service.reactivarDoctor(id);

	    if (!ok) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> desactivarDoctor(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		boolean ok = service.desactivarDoctor(id);

		if (!ok) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}
