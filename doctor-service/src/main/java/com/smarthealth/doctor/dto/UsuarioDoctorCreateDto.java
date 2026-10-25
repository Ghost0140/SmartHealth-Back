package com.smarthealth.doctor.dto;

public record UsuarioDoctorCreateDto(
		String correo,
		String clave,
		Integer idDoctor
) {}
