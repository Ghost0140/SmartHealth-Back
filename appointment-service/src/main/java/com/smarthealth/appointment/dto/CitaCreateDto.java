package com.smarthealth.appointment.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record CitaCreateDto(
		@NotNull(message = "El id del paciente es obligatorio")
		Integer idPaciente,

		@NotNull(message = "El id del doctor es obligatorio")
		Integer idDoctor,

		@NotNull(message = "La fecha de la cita es obligatoria")
	    @Future(message = "La fecha de la cita debe ser una fecha futura")
		LocalDateTime fecha
) {}
