package com.smarthealth.reception.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RecepcionistaCreateDto(
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
        String email
) {}
