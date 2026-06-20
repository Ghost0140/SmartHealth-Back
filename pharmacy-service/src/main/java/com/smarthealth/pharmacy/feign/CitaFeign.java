package com.smarthealth.pharmacy.feign;

import java.time.LocalDateTime;

public record CitaFeign(
		Long idCita,
		Integer idPaciente,
		Integer idDoctor,
		LocalDateTime fecha,
		Integer idEstado,
		String nombreEstado
) {}
