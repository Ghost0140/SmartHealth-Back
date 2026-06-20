package com.smarthealth.appointment.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.appointment.dto.CitaCreateDto;
import com.smarthealth.appointment.dto.CitaResponseDto;
import com.smarthealth.appointment.entity.CitaEntity;
import com.smarthealth.appointment.entity.EstadoEntity;
import com.smarthealth.appointment.event.CitaCreatedEvent;

@Component
public class CitaMapper {

	public CitaEntity toEntity(CitaCreateDto dto, EstadoEntity estado) {
		CitaEntity cita = new CitaEntity();

		cita.setIdPaciente(dto.idPaciente());
		cita.setIdDoctor(dto.idDoctor());
		cita.setFecha(dto.fecha());
		cita.setEstado(estado);

		return cita;
	}
	
	public CitaResponseDto toResponseDto(CitaEntity cita) {
        return new CitaResponseDto(
        		cita.getIdCita(),
        		cita.getIdPaciente(),
        		cita.getIdDoctor(),
        		cita.getFecha(),
        		cita.getEstado().getIdEstado(),
        		cita.getEstado().getNombre()
        );
    }
	
	public CitaCreatedEvent toEvent(CitaEntity entity) {
		return new CitaCreatedEvent(
				entity.getIdCita(),
				entity.getIdPaciente(),
				entity.getIdDoctor(),
				entity.getFecha(),
				entity.getEstado().getIdEstado(),
				entity.getEstado().getNombre()
		);
	}

}
