package com.smarthealth.pharmacy.event;

import java.time.LocalDateTime;

public record RecetaCreatedEvent(
		Long idReceta,
		Long idCita,
		Integer idPaciente,
		Integer idMedicamento,
		String nombreMedicamento,
		Integer cantidad,
		LocalDateTime fechaRegistro
) {}
