package com.smarthealth.appointment.dto;

import java.time.LocalDateTime;

public record CitaResponseDto(
		Long idCita,
		Integer idPaciente,
		Integer idDoctor,
		LocalDateTime fecha,
		Integer idEstado,
		String nombreEstado
) {}
