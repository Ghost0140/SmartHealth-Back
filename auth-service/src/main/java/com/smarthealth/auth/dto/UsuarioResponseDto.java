package com.smarthealth.auth.dto;

public record UsuarioResponseDto(
		Integer idUsuario,
        String correo,
        Integer idRol,
        String nombreRol,
        Integer idDoctor,
        Boolean activo
) {}
