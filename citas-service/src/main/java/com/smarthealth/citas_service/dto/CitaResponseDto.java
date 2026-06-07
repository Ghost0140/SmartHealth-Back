package com.smarthealth.citas_service.dto;

import java.time.LocalDateTime;

import com.smarthealth.citas_service.entity.EstadoCita;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitaResponseDto {
	
	private Integer idCita;

    private Integer idPaciente;

    private Integer idDoctor;

    private LocalDateTime fechaCita;

    private EstadoCita estado;

    private Boolean activo;

}
