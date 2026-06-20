package com.smarthealth.doctor.dto;

public record EspecialidadResponseDto(
		Integer idEspecialidad,
		String nombre,
		Boolean activo
) {}
