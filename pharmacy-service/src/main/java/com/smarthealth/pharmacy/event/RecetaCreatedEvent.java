package com.smarthealth.pharmacy.event;

import java.time.LocalDateTime;

public record RecetaCreatedEvent(
		Long idReceta,
		Long idCita,
		Integer idMedicamento,
		String nombreMedicamento,
		Integer cantidad,
		LocalDateTime fechaRegistro
) {}
