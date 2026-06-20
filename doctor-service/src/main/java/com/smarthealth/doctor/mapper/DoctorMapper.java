package com.smarthealth.doctor.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.doctor.dto.DoctorCreateDto;
import com.smarthealth.doctor.dto.DoctorResponseDto;
import com.smarthealth.doctor.dto.DoctorUpdateDto;
import com.smarthealth.doctor.entity.DoctorEntity;
import com.smarthealth.doctor.entity.EspecialidadEntity;

@Component
public class DoctorMapper {

	public DoctorEntity toEntity(DoctorCreateDto dto, EspecialidadEntity especialidad) {
		DoctorEntity doctor = new DoctorEntity();

		doctor.setEspecialidad(especialidad);
		doctor.setNombres(dto.nombres());
		doctor.setApellidos(dto.apellidos());
		doctor.setDni(dto.dni());
		doctor.setTelefono(dto.telefono());
		doctor.setEmail(dto.email());
		doctor.setDisponible(true);
		doctor.setActivo(true);

		return doctor;
	}
	
	public DoctorResponseDto toResponseDto(DoctorEntity doctor) {
        return new DoctorResponseDto(
                doctor.getIdDoctor(),
                doctor.getEspecialidad().getIdEspecialidad(),
                doctor.getEspecialidad().getNombre(),
                doctor.getNombres(),
                doctor.getApellidos(),
                doctor.getDni(),
                doctor.getTelefono(),
                doctor.getEmail(),
                doctor.getDisponible(),
                doctor.getActivo()
        );
    }
	
	public void updateEntity(
			DoctorUpdateDto dto,
			DoctorEntity doctor,
			EspecialidadEntity especialidad
	) {
		doctor.setEspecialidad(especialidad);
		doctor.setTelefono(dto.telefono());
		doctor.setEmail(dto.email());
		doctor.setDisponible(dto.disponible());
	}

}
