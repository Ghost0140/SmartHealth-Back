package com.smarthealth.pharmacy.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MedicamentoUpdateDto(
		@NotBlank
        @Size(max = 100)
		String nombre,

		@NotNull(message = "El stock es obligatorio")
		@Min(value = 0, message = "El stock no puede ser negativo")
		Integer stock,

		@NotNull(message = "El precio es obligatorio")
		@Positive(message = "El precio debe ser mayor que 0")
		BigDecimal precio
) {}
