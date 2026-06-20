package com.smarthealth.patient.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.patient.dto.PacienteCreateDto;
import com.smarthealth.patient.dto.PacienteResponseDto;
import com.smarthealth.patient.dto.PacienteUpdateDto;
import com.smarthealth.patient.entity.PacienteEntity;
import com.smarthealth.patient.mapper.PacienteMapper;
import com.smarthealth.patient.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {
	
	private final PacienteRepository repo;
	
	private final PacienteMapper mapper;
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	public List<PacienteResponseDto> listarPacientes(Boolean activo) {
		List<PacienteEntity> pacientes =
	            activo == null
	                    ? repo.findAll()
	                    : repo.findByActivo(activo);
		
		return pacientes.stream()
	            .map(mapper::toResponseDto)
	            .toList();
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	public Optional<PacienteResponseDto> obtenerPacientePorId(Integer id){
	    return repo.findById(id)
	            .map(mapper::toResponseDto);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	public PacienteResponseDto registrarPaciente(PacienteCreateDto dto) {
		if (repo.existsByDni(dto.dni())) {
		    throw new RuntimeException("DNI ya existe");
		}
		
		PacienteEntity entidad = mapper.toEntity(dto);

		PacienteEntity guardado = repo.save(entidad);

		return mapper.toResponseDto(guardado);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<PacienteResponseDto> actualizarPaciente(Integer id, PacienteUpdateDto dto) {		
		return repo.findById(id).map(existente -> {
			mapper.updateEntity(dto, existente);

			PacienteEntity guardado = repo.save(existente);

			return mapper.toResponseDto(guardado);
		});
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean desactivarPaciente(Integer id) {
		Optional<PacienteEntity> existente = repo.findById(id);
		if (existente.isPresent()) {
			PacienteEntity paciente = existente.get();
			paciente.setActivo(false);
			repo.save(paciente);
			return true;
		}
		return false;
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean reactivarPaciente(Integer id) {
		Optional<PacienteEntity> existente = repo.findById(id);
		if (existente.isPresent()) {
			PacienteEntity paciente = existente.get();
			paciente.setActivo(true);
			repo.save(paciente);
			return true;
		}
		return false;
	}

}
