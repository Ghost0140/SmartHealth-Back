package com.smarthealth.auth.dto;

public record LoginResponseDto(
		String token,
        Integer idUsuario,
        String correo,
        String rol,
        Integer idDoctor
) {}
