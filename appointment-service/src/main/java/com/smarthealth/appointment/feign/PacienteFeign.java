package com.smarthealth.appointment.feign;

public record PacienteFeign(
		Integer idPaciente,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String email,
        Boolean activo
) {}
