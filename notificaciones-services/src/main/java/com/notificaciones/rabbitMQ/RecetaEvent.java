package com.notificaciones.rabbitMQ;

import java.time.LocalDateTime;

/**
 * Debe coincidir exactamente con RecetaCreatedEvent de farmacia-service
 * (mismo orden de campos, mismos tipos, mismos nombres).
 */
public record RecetaEvent(
		Long idReceta,
		Long idCita,
		Integer idPaciente,
		Integer idMedicamento,
		String nombreMedicamento,
		Integer cantidad,
		LocalDateTime fechaRegistro
) {}
