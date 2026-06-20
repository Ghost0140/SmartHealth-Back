package com.smarthealth.doctor.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.doctor.dto.EspecialidadResponseDto;
import com.smarthealth.doctor.entity.EspecialidadEntity;

@Component
public class EspecialidadMapper {
	
	public EspecialidadResponseDto toResponseDto(EspecialidadEntity especialidad) {
		return new EspecialidadResponseDto(
				especialidad.getIdEspecialidad(),
				especialidad.getNombre(),
				especialidad.getActivo()
		);
	}

}
