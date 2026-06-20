package com.smarthealth.doctor.dto;

public record DoctorResponseDto(
		Integer idDoctor,
        Integer idEspecialidad,
        String nombreEspecialidad,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String email,
        Boolean disponible,
        Boolean activo
) {}
