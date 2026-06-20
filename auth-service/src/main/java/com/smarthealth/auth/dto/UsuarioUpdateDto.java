package com.smarthealth.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateDto(
		@NotBlank(message = "El correo es obligatorio")
        @Email(message = "Debe ingresar un correo válido")
        String correo
) {}
