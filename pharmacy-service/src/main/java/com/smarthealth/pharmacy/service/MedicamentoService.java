package com.smarthealth.pharmacy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.pharmacy.dto.MedicamentoCreateDto;
import com.smarthealth.pharmacy.dto.MedicamentoResponseDto;
import com.smarthealth.pharmacy.dto.MedicamentoUpdateDto;
import com.smarthealth.pharmacy.entity.MedicamentoEntity;
import com.smarthealth.pharmacy.mapper.MedicamentoMapper;
import com.smarthealth.pharmacy.repository.MedicamentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicamentoService {
	
	private final MedicamentoRepository repo;
	
	private final MedicamentoMapper mapper;
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public List<MedicamentoResponseDto> listarMedicamentos(Boolean activo) {
		List<MedicamentoEntity> medicamentos =
	            activo == null
	                    ? repo.findAll()
	                    : repo.findByActivo(activo);
		
		return medicamentos.stream()
	            .map(mapper::toResponseDto)
	            .toList();
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<MedicamentoResponseDto> obtenerMedicamentoPorId(Integer id){
	    return repo.findById(id)
	            .map(mapper::toResponseDto);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public MedicamentoResponseDto registrarMedicamento(MedicamentoCreateDto dto) {
		if (repo.existsByNombre(dto.nombre())) {
		    throw new RuntimeException("El nombre ya existe");
		}
		
		MedicamentoEntity entidad = mapper.toEntity(dto);

		MedicamentoEntity guardado = repo.save(entidad);

		return mapper.toResponseDto(guardado);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Optional<MedicamentoResponseDto> actualizarMedicamento(Integer id, MedicamentoUpdateDto dto) {
		if (repo.existsByNombreAndIdMedicamentoNot(dto.nombre(), id)) {
		    throw new RuntimeException("El nombre ya existe");
		}
		return repo.findById(id).map(existente -> {
			mapper.updateEntity(dto, existente);

			MedicamentoEntity guardado = repo.save(existente);

			return mapper.toResponseDto(guardado);
		});
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean desactivarMedicamento(Integer id) {
		Optional<MedicamentoEntity> existente = repo.findById(id);
		if (existente.isPresent()) {
			MedicamentoEntity medicamento = existente.get();
			medicamento.setActivo(false);
			repo.save(medicamento);
			return true;
		}
		return false;
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean reactivarMedicamento(Integer id) {
		Optional<MedicamentoEntity> existente = repo.findById(id);
		if (existente.isPresent()) {
			MedicamentoEntity medicamento = existente.get();
			medicamento.setActivo(true);
			repo.save(medicamento);
			return true;
		}
		return false;
	}

}
