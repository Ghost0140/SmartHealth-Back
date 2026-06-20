package com.smarthealth.appointment.event;

import java.time.LocalDateTime;

public record CitaCreatedEvent(
		Long idCita,
		Integer idPaciente,
		Integer idDoctor,
		LocalDateTime fecha,
		Integer idEstado,
		String nombreEstado
) {}
