package com.smarthealth.reception.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.reception.dto.RecepcionistaCreateDto;
import com.smarthealth.reception.dto.RecepcionistaResponseDto;
import com.smarthealth.reception.dto.RecepcionistaUpdateDto;
import com.smarthealth.reception.entity.RecepcionistaEntity;

@Component
public class RecepcionistaMapper {

	public RecepcionistaEntity toEntity(RecepcionistaCreateDto dto) {
		RecepcionistaEntity recepcion = new RecepcionistaEntity();
        
		recepcion.setNombres(dto.nombres());
		recepcion.setApellidos(dto.apellidos());
		recepcion.setDni(dto.dni());
		recepcion.setTelefono(dto.telefono());
		recepcion.setEmail(dto.email());
		recepcion.setActivo(true);
        
        return recepcion;
    }
	
	public RecepcionistaResponseDto toResponseDto(RecepcionistaEntity reception) {
		return new RecepcionistaResponseDto(
				reception.getIdRecepcionista(),
				reception.getNombres(),
                reception.getApellidos(),
                reception.getDni(),
                reception.getTelefono(),
                reception.getEmail(),
                reception.getActivo()
        );
    }
	
	public void updateEntity(RecepcionistaUpdateDto dto, RecepcionistaEntity reception) {
		reception.setTelefono(dto.telefono());
		reception.setEmail(dto.email());
	}

}
