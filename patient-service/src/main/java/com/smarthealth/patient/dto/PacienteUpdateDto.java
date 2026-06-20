package com.smarthealth.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PacienteUpdateDto(
        @NotBlank
        @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
        String telefono,

        @NotBlank
        @Email(message = "Email inválido")
        @Size(max = 100)
        String email
) {}
