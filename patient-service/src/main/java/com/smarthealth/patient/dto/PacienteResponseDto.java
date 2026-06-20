package com.smarthealth.patient.dto;

public record PacienteResponseDto(
        Integer idPaciente,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String email,
        Boolean activo
) {}
