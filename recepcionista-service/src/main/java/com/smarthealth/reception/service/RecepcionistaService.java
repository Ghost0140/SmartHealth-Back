package com.smarthealth.reception.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.reception.dto.RecepcionistaCreateDto;
import com.smarthealth.reception.dto.RecepcionistaResponseDto;
import com.smarthealth.reception.dto.RecepcionistaUpdateDto;
import com.smarthealth.reception.entity.RecepcionistaEntity;
import com.smarthealth.reception.mapper.RecepcionistaMapper;
import com.smarthealth.reception.repository.RecepcionistaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecepcionistaService {

	private final RecepcionistaRepository repo;
	
	private final RecepcionistaMapper mapper;
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public List<RecepcionistaResponseDto> listarRecepcionista(Boolean activo) {
		List<RecepcionistaEntity> reception =
	            activo == null
	                    ? repo.findAll()
	                    : repo.findByActivo(activo);
		
		return reception.stream()
	            .map(mapper::toResponseDto)
	            .toList();
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<RecepcionistaResponseDto> obtenerRecepcionistaPorId(Integer id){
	    return repo.findById(id)
	            .map(mapper::toResponseDto);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public RecepcionistaResponseDto registrarRecepcionista(RecepcionistaCreateDto dto) {
		if (repo.existsByDni(dto.dni())) {
		    throw new RuntimeException("DNI ya existe");
		}
		
		RecepcionistaEntity entidad = mapper.toEntity(dto);

		RecepcionistaEntity guardado = repo.save(entidad);

		return mapper.toResponseDto(guardado);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<RecepcionistaResponseDto> actualizarRecepcionista(Integer id, RecepcionistaUpdateDto dto) {		
		return repo.findById(id).map(existente -> {
			mapper.updateEntity(dto, existente);

			RecepcionistaEntity guardado = repo.save(existente);

			return mapper.toResponseDto(guardado);
		});
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean desactivarRecepcionista(Integer id) {
		Optional<RecepcionistaEntity> existente = repo.findById(id);
		if (existente.isPresent()) {
			RecepcionistaEntity reception = existente.get();
			reception.setActivo(false);
			repo.save(reception);
			return true;
		}
		return false;
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean reactivarRecepcionista(Integer id) {
		Optional<RecepcionistaEntity> existente = repo.findById(id);
		if (existente.isPresent()) {
			RecepcionistaEntity reception = existente.get();
			reception.setActivo(true);
			repo.save(reception);
			return true;
		}
		return false;
	}

}
