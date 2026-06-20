package com.smarthealth.reception.dto;

public record RecepcionistaResponseDto(
		Integer idRecepcionista,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String email,
        Boolean activo
) {}
