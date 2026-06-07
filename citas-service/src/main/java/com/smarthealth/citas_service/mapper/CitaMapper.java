package com.smarthealth.citas_service.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.citas_service.dto.CitaCreateDto;
import com.smarthealth.citas_service.dto.CitaResponseDto;
import com.smarthealth.citas_service.dto.CitaUpdateDto;
import com.smarthealth.citas_service.entity.CitaEntity;
import com.smarthealth.citas_service.entity.EstadoCita;
import com.smarthealth.citas_service.event.CitaEvent;

@Component
public class CitaMapper {
	
	public CitaEntity toEntity(CitaCreateDto dto) {
        CitaEntity cita = new CitaEntity();

        cita.setIdPaciente(dto.getIdPaciente());
        cita.setIdDoctor(dto.getIdDoctor());
        cita.setFechaCita(dto.getFechaCita());
        cita.setEstado(EstadoCita.PROGRAMADA);
        cita.setActivo(true);

        return cita;
    }
	
	public CitaEvent toEvent(CitaEntity entity) {
		return new CitaEvent(
				entity.getIdCita(),
				entity.getIdPaciente(),
				entity.getIdDoctor(),
				entity.getFechaCita(),
				entity.getEstado(),
				entity.getActivo()
		);
	}
	
	public CitaResponseDto toResponseDTO(CitaEntity cita) {
        CitaResponseDto dto = new CitaResponseDto();

        dto.setIdCita(cita.getIdCita());
        dto.setIdPaciente(cita.getIdPaciente());
        dto.setIdDoctor(cita.getIdDoctor());
        dto.setFechaCita(cita.getFechaCita());
        dto.setEstado(cita.getEstado());
        dto.setActivo(cita.getActivo());

        return dto;
    }
	
	public void updateEntity(CitaEntity entity, CitaUpdateDto dto) {
	    entity.setIdPaciente(dto.getIdPaciente());
	    entity.setIdDoctor(dto.getIdDoctor());
	    entity.setFechaCita(dto.getFechaCita());
	    entity.setEstado(dto.getEstado());
	}

}
