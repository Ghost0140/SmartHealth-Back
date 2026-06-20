package com.smarthealth.auth.controller;

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

import com.smarthealth.auth.dto.CambiarClaveDto;
import com.smarthealth.auth.dto.LoginRequestDto;
import com.smarthealth.auth.dto.LoginResponseDto;
import com.smarthealth.auth.dto.ResetClaveDto;
import com.smarthealth.auth.dto.UsuarioCreateDto;
import com.smarthealth.auth.dto.UsuarioResponseDto;
import com.smarthealth.auth.dto.UsuarioUpdateDto;
import com.smarthealth.auth.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	private final UsuarioService service;
	
	@GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios(
            @RequestParam(required = false) Boolean activo
    ) {
		List<UsuarioResponseDto> lista = service.listarUsuarios(activo);
		
		if (lista.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }

	    return ResponseEntity.ok(lista);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorId(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.obtenerUsuarioPorId(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
		LoginResponseDto response = service.login(dto);
	    return ResponseEntity.ok(response);
	}

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> registrarUsuario(@Valid @RequestBody UsuarioCreateDto dto) {
    	UsuarioResponseDto creado = service.registrarUsuario(dto);
    	return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioUpdateDto dto
    ) {
    	if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		return service.actualizarUsuario(id, dto)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
    }

	@PutMapping("/mi-clave")
	public ResponseEntity<Void> cambiarMiClave(@Valid @RequestBody CambiarClaveDto dto) {
		service.cambiarMiClave(dto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/reset-clave/{id}")
	public ResponseEntity<Void> resetClave(@PathVariable Integer id, @Valid @RequestBody ResetClaveDto dto) {
		service.resetClave(id, dto);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/reactivar/{id}")
	public ResponseEntity<Void> reactivarUsuario(@PathVariable Integer id) {
	    if (id <= 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    boolean ok = service.reactivarUsuario(id);

	    if (!ok) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> desactivarUsuario(@PathVariable Integer id) {
		if (id <= 0) {
			return ResponseEntity.badRequest().build();
		}

		boolean ok = service.desactivarUsuario(id);

		if (!ok) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}
