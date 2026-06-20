package com.smarthealth.appointment.feign;

public record DoctorFeign(
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
