package com.smarthealth.pharmacy.dto;

import java.time.LocalDateTime;

public record RecetaResponseDto(
		Long idReceta,
		Long idCita,
		Integer idMedicamento,
		String nombreMedicamento,
		Integer cantidad,
		LocalDateTime fechaRegistro
) {}
