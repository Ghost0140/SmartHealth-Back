package com.smarthealth.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.doctor.dto.EspecialidadResponseDto;
import com.smarthealth.doctor.entity.EspecialidadEntity;
import com.smarthealth.doctor.mapper.EspecialidadMapper;
import com.smarthealth.doctor.repository.EspecialidadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecialidadService {
	
	private final EspecialidadRepository repo;
	
	private final EspecialidadMapper mapper;
	
	@Transactional(readOnly = true)
	@PreAuthorize("isAuthenticated()")
	public List<EspecialidadResponseDto> listarEspecialidades(Boolean activo) {
	    List<EspecialidadEntity> especialidades =
	            activo == null
	                    ? repo.findAll()
	                    : repo.findByActivo(activo);

	    return especialidades.stream()
	            .map(mapper::toResponseDto)
	            .toList();
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("isAuthenticated()")
	public Optional<EspecialidadResponseDto> obtenerEspecialidadPorId(Integer id){
	    return repo.findById(id)
	            .map(mapper::toResponseDto);
	}

}
