package com.smarthealth.citas_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smarthealth.citas_service.dto.CitaCreateDto;
import com.smarthealth.citas_service.dto.CitaResponseDto;
import com.smarthealth.citas_service.dto.CitaUpdateDto;
import com.smarthealth.citas_service.entity.CitaEntity;
import com.smarthealth.citas_service.event.CitaEvent;
import com.smarthealth.citas_service.feign.ClienteDoctorFeign;
import com.smarthealth.citas_service.feign.ClientePacienteFeign;
import com.smarthealth.citas_service.feign.DoctorFeign;
import com.smarthealth.citas_service.feign.PacienteFeign;
import com.smarthealth.citas_service.mapper.CitaMapper;
import com.smarthealth.citas_service.rabbit.CitaProducer;
import com.smarthealth.citas_service.repository.CitaRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

	private final CitaRepository repo;

	private final CitaMapper mapper;
	
	private final ClientePacienteFeign clientePaciente;
	
	private final ClienteDoctorFeign clienteDoctor;
	
	private final CitaProducer producer;

	public List<CitaResponseDto> listarCitas() {
	    return repo.findAll()
	            .stream()
	            .map(mapper::toResponseDTO)
	            .toList();
	}

	public Optional<CitaResponseDto> obtenerCitaPorId(Integer id){
	    return repo.findById(id)
	            .map(mapper::toResponseDTO);
	}

	public CitaResponseDto crearCita(CitaCreateDto dto) {
		try {
			PacienteFeign paciente = clientePaciente.obtenerPacientePorId(dto.getIdPaciente());

			if (paciente.getEstado() == null || !paciente.getEstado()) {
				throw new RuntimeException("El paciente no está activo");
			}
		} catch (FeignException.NotFound e) {
			throw new RuntimeException("El paciente no existe");
		}

		try {
			DoctorFeign doctor = clienteDoctor.obtenerDoctorPorId(dto.getIdDoctor());

			if (doctor.getEstado() == null || !doctor.getEstado()
					|| "NO_DISPONIBLE".equals(doctor.getDisponibilidad())
			) {
				throw new RuntimeException("El doctor no está activo o no está disponible");
			}
		} catch (FeignException.NotFound e) {
			throw new RuntimeException("El doctor no existe");
		}

		CitaEntity entidad = mapper.toEntity(dto);

		CitaEntity guardado = repo.save(entidad);
		
		CitaEvent event = mapper.toEvent(guardado);
		
		producer.publicarCita(event);

		return mapper.toResponseDTO(guardado);
	}

	public Optional<CitaResponseDto> actualizarCita(Integer id, CitaUpdateDto dto) {
		return repo.findById(id).map(existente -> {
			try {
				PacienteFeign paciente = clientePaciente.obtenerPacientePorId(dto.getIdPaciente());

				if (paciente.getEstado() == null || !paciente.getEstado()) {
					throw new RuntimeException("El paciente no está activo");
				}
			} catch (FeignException.NotFound e) {
				throw new RuntimeException("El paciente no existe");
			}

			try {
				DoctorFeign doctor = clienteDoctor.obtenerDoctorPorId(dto.getIdDoctor());

				if (doctor.getEstado() == null || !doctor.getEstado()
						|| "NO_DISPONIBLE".equals(doctor.getDisponibilidad())
				) {
					throw new RuntimeException("El doctor no está activo o no está disponible");
				}
			} catch (FeignException.NotFound e) {
				throw new RuntimeException("El doctor no existe");
			}

			mapper.updateEntity(existente, dto);

			CitaEntity guardada = repo.save(existente);

			return mapper.toResponseDTO(guardada);
		});
	}

	public boolean desactivarCita(Integer id) {
		Optional<CitaEntity> existente = repo.findById(id);
		if (existente.isPresent()) {
			CitaEntity cita = existente.get();
			cita.setActivo(false);
			repo.save(cita);
			return true;
		}
		return false;
	}

}
