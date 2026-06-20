package com.smarthealth.pharmacy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecetaCreateDto(
		@NotNull(message = "El id de la cita es obligatoria")
		Long idCita,

		@NotNull(message = "El id del medicamento es obligatorio")
		Integer idMedicamento,

		@NotNull(message = "La cantidad es obligatoria")
		@Positive(message = "La cantidad debe ser mayor que 0")
		Integer cantidad
) {}
