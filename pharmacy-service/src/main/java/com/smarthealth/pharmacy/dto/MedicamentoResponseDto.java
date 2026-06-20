package com.smarthealth.pharmacy.dto;

import java.math.BigDecimal;

public record MedicamentoResponseDto(
		Integer idMedicamento,
		String nombre,
		Integer stock,
		BigDecimal precio,
		Boolean activo
) {}
