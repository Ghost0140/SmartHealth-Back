package com.smarthealth.citas_service.event;

import java.time.LocalDateTime;

import com.smarthealth.citas_service.entity.EstadoCita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CitaEvent {
	
	private Integer idCita;

	private Integer idPaciente;

	private Integer idDoctor;

	private LocalDateTime fechaCita;

	private EstadoCita estado;

	private Boolean activo;

}
