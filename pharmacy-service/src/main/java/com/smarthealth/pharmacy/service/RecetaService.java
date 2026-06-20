package com.smarthealth.pharmacy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.pharmacy.dto.RecetaCreateDto;
import com.smarthealth.pharmacy.dto.RecetaResponseDto;
import com.smarthealth.pharmacy.entity.MedicamentoEntity;
import com.smarthealth.pharmacy.entity.RecetaEntity;
import com.smarthealth.pharmacy.event.RecetaCreatedEvent;
import com.smarthealth.pharmacy.feign.CitaFeign;
import com.smarthealth.pharmacy.feign.ClienteCitaFeign;
import com.smarthealth.pharmacy.mapper.RecetaMapper;
import com.smarthealth.pharmacy.rabbit.RecetaProducer;
import com.smarthealth.pharmacy.repository.MedicamentoRepository;
import com.smarthealth.pharmacy.repository.RecetaRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecetaService {
	
	private final RecetaRepository recetaRepo;
	
	private final MedicamentoRepository medicamentoRepo;
	
	private final RecetaMapper mapper;
	
	private final ClienteCitaFeign clienteCita;
	
	private final RecetaProducer producer;
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPCIONISTA')")
	public List<RecetaResponseDto> listarRecetas(String medicamento) {
        List<RecetaEntity> recetas =
                medicamento == null
                        ? recetaRepo.findAllWithMedicamento()
                        : recetaRepo.findByNombreWithNombre(medicamento);

        return recetas.stream()
                .map(mapper::toResponseDto)
                .toList();
    }
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPCIONISTA')")
    public Optional<RecetaResponseDto> obtenerRecetaPorId(Long id) {
        return recetaRepo.findByIdWithMedicamento(id)
                .map(mapper::toResponseDto);
    }
	
	@Transactional
	@PreAuthorize("hasRole('DOCTOR')")
    public RecetaResponseDto registrarReceta(RecetaCreateDto dto) {
		CitaFeign cita;
		try {
			cita = clienteCita.obtenerCitaPorId(dto.idCita());
			if ("CANCELADA".equals(cita.nombreEstado()) || "ATENDIDA".equals(cita.nombreEstado())) {
                throw new RuntimeException("La cita ya fue atendida o cancelada");
            }
		} catch (FeignException.NotFound e) {
			throw new RuntimeException("La cita no existe");
		} catch (FeignException e) {
	        throw new RuntimeException("Error al consultar el servicio de citas");
	    }
		
		MedicamentoEntity medicamento = medicamentoRepo
				.findById(dto.idMedicamento())
				.orElseThrow(() -> new RuntimeException("El medicamento no existe"));
		
		if (!medicamento.getActivo()) {
			 throw new RuntimeException("El medicamento está inactivo");
		}
		
		if (medicamento.getStock() < dto.cantidad()) {
            throw new RuntimeException("Stock insuficiente para el medicamento");
        }
		
		medicamento.setStock(medicamento.getStock() - dto.cantidad());
		
		medicamentoRepo.save(medicamento);
		
        RecetaEntity entidad = mapper.toEntity(dto, medicamento);

        RecetaEntity guardada = recetaRepo.save(entidad);
        
        RecetaCreatedEvent event = mapper.toEvent(guardada, cita.idPaciente());
        
        clienteCita.atenderCita(dto.idCita());
        
        producer.publicarReceta(event);

        return mapper.toResponseDto(guardada);
    }

}
