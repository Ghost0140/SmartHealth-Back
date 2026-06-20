package com.smarthealth.auth.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.auth.dto.RolResponseDto;
import com.smarthealth.auth.entity.RolEntity;

@Component
public class RolMapper {
	
	public RolResponseDto toResponseDto(RolEntity rol) {
		return new RolResponseDto(
				rol.getIdRol(),
				rol.getNombre(),
				rol.getActivo()
		);
	}

}
