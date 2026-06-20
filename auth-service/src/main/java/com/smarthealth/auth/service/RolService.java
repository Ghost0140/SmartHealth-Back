package com.smarthealth.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.auth.dto.RolResponseDto;
import com.smarthealth.auth.entity.RolEntity;
import com.smarthealth.auth.mapper.RolMapper;
import com.smarthealth.auth.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {
	
	private final RolRepository repo;
	
	private final RolMapper mapper;
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public List<RolResponseDto> listaRoles(Boolean activo) {
	    List<RolEntity> roles =
	            activo == null
	                    ? repo.findAll()
	                    : repo.findByActivo(activo);

	    return roles.stream()
	            .map(mapper::toResponseDto)
	            .toList();
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<RolResponseDto> obtenerRolPorId(Integer id){
	    return repo.findById(id)
	            .map(mapper::toResponseDto);
	}

}
