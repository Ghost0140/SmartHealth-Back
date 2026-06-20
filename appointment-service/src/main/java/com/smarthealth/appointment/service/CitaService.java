package com.smarthealth.appointment.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.appointment.dto.CitaCreateDto;
import com.smarthealth.appointment.dto.CitaResponseDto;
import com.smarthealth.appointment.dto.UsuarioAutenticadoDto;
import com.smarthealth.appointment.entity.CitaEntity;
import com.smarthealth.appointment.entity.EstadoEntity;
import com.smarthealth.appointment.event.CitaCreatedEvent;
import com.smarthealth.appointment.feign.ClienteDoctorFeign;
import com.smarthealth.appointment.feign.ClientePacienteFeign;
import com.smarthealth.appointment.feign.DoctorFeign;
import com.smarthealth.appointment.feign.PacienteFeign;
import com.smarthealth.appointment.mapper.CitaMapper;
import com.smarthealth.appointment.rabbit.CitaProducer;
import com.smarthealth.appointment.repository.CitaRepository;
import com.smarthealth.appointment.repository.EstadoRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

	private final CitaRepository citaRepo;

	private final EstadoRepository estadoRepo;

	private final CitaMapper mapper;

	private final ClientePacienteFeign clientePaciente;

	private final ClienteDoctorFeign clienteDoctor;

	private final CitaProducer producer;
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPCIONISTA')")
	public List<CitaResponseDto> listarCitas(String estado) {
		UsuarioAutenticadoDto usuario =
	            (UsuarioAutenticadoDto) SecurityContextHolder
	            	.getContext()
	            	.getAuthentication()
	            	.getPrincipal();
		
		List<CitaEntity> citas;
		
		if ("DOCTOR".equals(usuario.rol())) {
	        citas = 
	        		estado == null
	                        ? citaRepo.findByIdDoctorWithEstado(usuario.idDoctor())
	                        : citaRepo.findByIdDoctorAndEstadoWithEstado(
	                                usuario.idDoctor(),
	                                estado
	                        );
	    } else {
	        citas =
	                estado == null
	                        ? citaRepo.findAllWithEstado()
	                        : citaRepo.findByEstadoWithEstado(estado);

	    }

	    return citas.stream()
	            .map(mapper::toResponseDto)
	            .toList();
    }
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPCIONISTA')")
    public Optional<CitaResponseDto> obtenerCitaPorId(Long id) {
		UsuarioAutenticadoDto usuario =
	            (UsuarioAutenticadoDto)
	                    SecurityContextHolder
	                            .getContext()
	                            .getAuthentication()
	                            .getPrincipal();
		
		Optional<CitaEntity> citaOpt = citaRepo.findByIdWithEstado(id);

	    if (citaOpt.isEmpty()) {
	        return Optional.empty();
	    }
	    
	    CitaEntity cita = citaOpt.get();

	    if ("DOCTOR".equals(usuario.rol())
	            && !cita.getIdDoctor().equals(usuario.idDoctor())) {
			throw new RuntimeException("No tiene permisos para acceder a esta cita");
	    }
		
	    return Optional.of(mapper.toResponseDto(cita));
    }
	
	@Transactional
	@PreAuthorize("hasRole('RECEPCIONISTA')")
    public CitaResponseDto registrarCita(CitaCreateDto dto) {
		LocalTime hora = dto.fecha().toLocalTime();

		if (hora.getSecond() != 0 || hora.getNano() != 0) {
		    throw new RuntimeException("La hora debe estar en formato HH:mm.");
		}

		if (hora.getMinute() != 0 && hora.getMinute() != 30) {
		    throw new RuntimeException("Solo se permiten citas cada 30 minutos.");
		}

		if (hora.isBefore(LocalTime.of(8, 0))
		        || hora.isAfter(LocalTime.of(17, 30))) {
		    throw new RuntimeException("Solo se permiten citas entre 08:00 y 18:00.");
		}
		
		try {
			PacienteFeign paciente = clientePaciente.obtenerPacientePorId(dto.idPaciente());
			if (!paciente.activo()) {
				throw new RuntimeException("El paciente no está activo");
			}
		} catch (FeignException.NotFound e) {
			throw new RuntimeException("El paciente no existe");
		} catch (FeignException e) {
	        throw new RuntimeException("Error al consultar el servicio de pacientes");
	    }
		
		try {
			DoctorFeign doctor = clienteDoctor.obtenerDoctorPorId(dto.idDoctor());
			if (!doctor.activo() || !doctor.disponible()) {
				throw new RuntimeException("El doctor no está activo o no está disponible");
			}
		} catch (FeignException.NotFound e) {
			throw new RuntimeException("El doctor no existe");
		} catch (FeignException e) {
	        throw new RuntimeException("Error al consultar el servicio de doctores");
	    }
		
		if (citaRepo.existsByIdDoctorAndFecha(dto.idDoctor(), dto.fecha())) {
			throw new RuntimeException(
					"El doctor ya tiene una cita programada para la fecha y hora seleccionadas."
			);
		}
		
		EstadoEntity estado = estadoRepo
				.findByNombreIgnoreCaseAndActivoTrue("PROGRAMADA")
				.orElseThrow(() -> new RuntimeException("El estado no existe o está inactivo."));
		
        CitaEntity entidad = mapper.toEntity(dto, estado);

        CitaEntity guardada = citaRepo.save(entidad);
        
        CitaCreatedEvent event = mapper.toEvent(guardada);
        
        producer.publicarCita(event);

        return mapper.toResponseDto(guardada);
    }
	
	@Transactional
	@PreAuthorize("hasRole('DOCTOR')")
	public boolean atenderCita(Long id) {
		UsuarioAutenticadoDto usuario =
	            (UsuarioAutenticadoDto)
	                    SecurityContextHolder
	                            .getContext()
	                            .getAuthentication()
	                            .getPrincipal();
		
        Optional<CitaEntity> existente = citaRepo.findByIdWithEstado(id);
        
        if (existente.isPresent()) {
            CitaEntity cita = existente.get();
			if (!cita.getIdDoctor().equals(usuario.idDoctor())) {
				throw new RuntimeException("No tiene permisos para atender esta cita");
			}
            if ("CANCELADA".equals(cita.getEstado().getNombre())) {
                throw new RuntimeException("No se puede atender una cita cancelada");
            }
            if ("ATENDIDA".equals(cita.getEstado().getNombre())) {
                throw new RuntimeException("La cita ya fue atendida");
            }
            EstadoEntity estado = estadoRepo
    				.findByNombreIgnoreCaseAndActivoTrue("ATENDIDA")
    				.orElseThrow(() -> new RuntimeException("El estado no existe o está inactivo."));
            cita.setEstado(estado);
            citaRepo.save(cita);
            return true;
        }
        return false;
    }
	
	@Transactional
	@PreAuthorize("hasRole('DOCTOR')")
	public boolean cancelarCita(Long id) {
		UsuarioAutenticadoDto usuario =
	            (UsuarioAutenticadoDto)
	                    SecurityContextHolder
	                            .getContext()
	                            .getAuthentication()
	                            .getPrincipal();
		
        Optional<CitaEntity> existente = citaRepo.findByIdWithEstado(id);
        
        if (existente.isPresent()) {
            CitaEntity cita = existente.get();
			if (!cita.getIdDoctor().equals(usuario.idDoctor())) {
				throw new RuntimeException("No tiene permisos para cancelar esta cita");
			}
            if ("ATENDIDA".equals(cita.getEstado().getNombre())) {
                throw new RuntimeException("No se puede cancelar una cita atendida");
            }
            if ("CANCELADA".equals(cita.getEstado().getNombre())) {
                throw new RuntimeException("La cita ya fue cancelada");
            }
            EstadoEntity estado = estadoRepo
    				.findByNombreIgnoreCaseAndActivoTrue("CANCELADA")
    				.orElseThrow(() -> new RuntimeException("El estado no existe o está inactivo."));
            cita.setEstado(estado);
            citaRepo.save(cita);
            return true;
        }
        return false;
    }

}
