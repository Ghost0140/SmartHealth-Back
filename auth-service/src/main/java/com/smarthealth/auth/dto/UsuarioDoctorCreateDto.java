package com.smarthealth.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioDoctorCreateDto(
		@NotBlank(message = "El correo es obligatorio")
		@Email(message = "Debe ingresar un correo válido")
		@Size(max = 100)
		String correo,

		@NotBlank(message = "La clave es obligatoria")
		@Size(min = 8, max = 64, message = "La clave debe tener entre 8 y 64 caracteres")
		@Pattern(
				regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._#\\-]).*$",
				message = "La clave debe contener mayúscula, minúscula, número y carácter especial"
		)
		String clave,

		@NotNull(message = "El doctor asociado es obligatorio")
		Integer idDoctor
) {}
