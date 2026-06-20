package com.smarthealth.patient.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.patient.dto.PacienteCreateDto;
import com.smarthealth.patient.dto.PacienteResponseDto;
import com.smarthealth.patient.dto.PacienteUpdateDto;
import com.smarthealth.patient.entity.PacienteEntity;

@Component
public class PacienteMapper {
	
	public PacienteEntity toEntity(PacienteCreateDto dto) {
        PacienteEntity paciente = new PacienteEntity();
        
        paciente.setNombres(dto.nombres());
        paciente.setApellidos(dto.apellidos());
        paciente.setDni(dto.dni());
        paciente.setTelefono(dto.telefono());
        paciente.setEmail(dto.email());
        paciente.setActivo(true);
        
        return paciente;
    }
	
	public PacienteResponseDto toResponseDto(PacienteEntity paciente) {
		return new PacienteResponseDto(
                paciente.getIdPaciente(),
                paciente.getNombres(),
                paciente.getApellidos(),
                paciente.getDni(),
                paciente.getTelefono(),
                paciente.getEmail(),
                paciente.getActivo()
        );
    }
	
	public void updateEntity(PacienteUpdateDto dto, PacienteEntity paciente) {
		paciente.setTelefono(dto.telefono());
		paciente.setEmail(dto.email());
	}

}
