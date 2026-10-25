package com.smarthealth.doctor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record DoctorCreateDto(
		@NotNull
        Integer idEspecialidad,

        @NotBlank
        @Size(max = 100)
        String nombres,

        @NotBlank
        @Size(max = 100)
        String apellidos,

        @NotBlank
        @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener 8 dígitos")
        String dni,

        @NotBlank
        @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
        String telefono,

        @NotBlank
        @Email(message = "Email inválido")
        @Size(max = 100)
        String email,

        @NotBlank(message = "La clave inicial es obligatoria")
		@Size(min = 8, max = 64, message = "La clave debe tener entre 8 y 64 caracteres")
		@Pattern(
				regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._#\\-]).*$",
				message = "La clave debe contener mayúscula, minúscula, número y carácter especial"
		)
		String claveInicial
) {}
