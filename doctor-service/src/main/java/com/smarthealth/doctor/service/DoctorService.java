package com.smarthealth.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.doctor.dto.DoctorCreateDto;
import com.smarthealth.doctor.dto.DoctorResponseDto;
import com.smarthealth.doctor.dto.DoctorUpdateDto;
import com.smarthealth.doctor.entity.DoctorEntity;
import com.smarthealth.doctor.entity.EspecialidadEntity;
import com.smarthealth.doctor.mapper.DoctorMapper;
import com.smarthealth.doctor.repository.DoctorRepository;
import com.smarthealth.doctor.repository.EspecialidadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
	
	private final DoctorRepository doctorRepo;
	
    private final EspecialidadRepository especialidadRepo;
    
    private final DoctorMapper mapper;
    
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
    public List<DoctorResponseDto> listarDoctores(Boolean activo) {
        List<DoctorEntity> doctores =
                activo == null
                        ? doctorRepo.findAllWithEspecialidad()
                        : doctorRepo.findByActivoWithEspecialidad(activo);

        return doctores.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    // Sin @PreAuthorize a propósito: auth-service consulta este método (vía Feign)
    // durante el login de un usuario DOCTOR, antes de que exista un JWT que enviar.
    // El SecurityConfig ya deja este endpoint como público (permitAll en GET /api/doctores/*).
    @Transactional(readOnly = true)
    public Optional<DoctorResponseDto> obtenerDoctorPorId(Integer id) {
        return doctorRepo.findByIdWithEspecialidad(id)
                .map(mapper::toResponseDto);
    }
    
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDto registrarDoctor(DoctorCreateDto dto) {
        if (doctorRepo.existsByDni(dto.dni())) {
            throw new RuntimeException("El DNI ya existe.");
        }

        EspecialidadEntity especialidad = especialidadRepo
                .findByIdEspecialidadAndActivoTrue(dto.idEspecialidad())
                .orElseThrow(() -> new RuntimeException("La especialidad no existe o está inactiva."));

        DoctorEntity entidad = mapper.toEntity(dto, especialidad);

        DoctorEntity guardado = doctorRepo.save(entidad);

        return mapper.toResponseDto(guardado);
    }
    
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
	public Optional<DoctorResponseDto> actualizarDoctor(Integer id, DoctorUpdateDto dto) {
		return doctorRepo.findByIdWithEspecialidad(id).map(doctor -> {
			EspecialidadEntity especialidad = especialidadRepo
					.findByIdEspecialidadAndActivoTrue(dto.idEspecialidad())
					.orElseThrow(() -> new RuntimeException("La especialidad no existe o está inactiva."));

			mapper.updateEntity(dto, doctor, especialidad);

			DoctorEntity guardado = doctorRepo.save(doctor);

			return mapper.toResponseDto(guardado);
		});
	}
	
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
	public boolean desactivarDoctor(Integer id) {
        Optional<DoctorEntity> existente = doctorRepo.findById(id);
        if (existente.isPresent()) {
            DoctorEntity doctor = existente.get();
            doctor.setActivo(false);
            doctorRepo.save(doctor);
            return true;
        }
        return false;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public boolean reactivarDoctor(Integer id) {
        Optional<DoctorEntity> existente = doctorRepo.findById(id);
        if (existente.isPresent()) {
            DoctorEntity doctor = existente.get();
            doctor.setActivo(true);
            doctorRepo.save(doctor);
            return true;
        }
        return false;
    }

}
