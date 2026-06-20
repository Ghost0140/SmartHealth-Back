package com.smarthealth.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetClaveDto(
		@NotBlank(message = "La clave es obligatoria")
		@Size(
				min = 8,
				max = 64,
				message = "La clave debe tener entre 8 y 64 caracteres"
		)
		@Pattern(
	            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._#\\-]).*$",
	            message = "La clave debe contener al menos una mayúscula, una minúscula, un número y un carácter especial"
	    )
        String nuevaClave
) {}
