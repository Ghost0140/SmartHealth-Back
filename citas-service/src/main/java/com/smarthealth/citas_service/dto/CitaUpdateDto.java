package com.smarthealth.citas_service.dto;

import java.time.LocalDateTime;

import com.smarthealth.citas_service.entity.EstadoCita;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CitaUpdateDto {

	@NotNull(message = "El id del paciente es obligatorio")
	private Integer idPaciente;

	@NotNull(message = "El id del doctor es obligatorio")
	private Integer idDoctor;

	@NotNull(message = "La fecha de la cita es obligatoria")
    @Future(message = "La fecha de la cita debe ser una fecha futura")
	private LocalDateTime fechaCita;

	@NotNull(message = "El estado de la cita es obligatorio")
	private EstadoCita estado;

}
