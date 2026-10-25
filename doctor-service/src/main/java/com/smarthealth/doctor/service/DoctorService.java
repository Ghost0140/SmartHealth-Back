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
import com.smarthealth.doctor.feign.AuthUserClient;
import com.smarthealth.doctor.dto.UsuarioDoctorCreateDto;
import feign.FeignException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
	
	private final DoctorRepository doctorRepo;
	
    private final EspecialidadRepository especialidadRepo;
    
    private final DoctorMapper mapper;

	private final AuthUserClient authUserClient;
    
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA','DOCTOR')")
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
    
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDto registrarDoctor(DoctorCreateDto dto) {
        if (doctorRepo.existsByDni(dto.dni())) {
            throw new IllegalStateException("El DNI ya existe.");
        }
		if (doctorRepo.existsByEmailIgnoreCase(dto.email().trim())) {
			throw new IllegalStateException("El correo ya está asociado a otro doctor.");
		}

        EspecialidadEntity especialidad = especialidadRepo
                .findByIdEspecialidadAndActivoTrue(dto.idEspecialidad())
                .orElseThrow(() -> new RuntimeException("La especialidad no existe o está inactiva."));

        DoctorEntity entidad = mapper.toEntity(dto, especialidad);

        DoctorEntity guardado = doctorRepo.saveAndFlush(entidad);
        DoctorResponseDto respuesta = mapper.toResponseDto(guardado);

		try {
			authUserClient.crearUsuarioDoctor(new UsuarioDoctorCreateDto(
					dto.email().trim(), dto.claveInicial(), guardado.getIdDoctor()));
		} catch (FeignException e) {
			eliminarPerfilSiFalloLaCuenta(guardado.getIdDoctor());
			if (e.status() == 409) {
				throw new IllegalStateException("El correo ya tiene una cuenta o este doctor ya tiene usuario.", e);
			}
			throw new RuntimeException(
					"No se pudo crear la cuenta del doctor (Auth respondió HTTP " + e.status() + "). "
							+ "Detalle: " + e.contentUTF8(), e);
		}

        return respuesta;
    }

	private void eliminarPerfilSiFalloLaCuenta(Integer idDoctor) {
		try {
			doctorRepo.deleteById(idDoctor);
		} catch (RuntimeException errorEliminacion) {
			throw new RuntimeException(
					"Auth no creó la cuenta y tampoco se pudo revertir el perfil del doctor " + idDoctor
							+ ". Revisa el registro manualmente.", errorEliminacion);
		}
	}
    
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
	public Optional<DoctorResponseDto> actualizarDoctor(Integer id, DoctorUpdateDto dto) {
		return doctorRepo.findByIdWithEspecialidad(id).map(doctor -> {
			if (doctorRepo.existsByEmailIgnoreCaseAndIdDoctorNot(dto.email().trim(), id)) {
				throw new IllegalStateException("El correo ya está asociado a otro doctor.");
			}
			EspecialidadEntity especialidad = especialidadRepo
					.findByIdEspecialidadAndActivoTrue(dto.idEspecialidad())
					.orElseThrow(() -> new RuntimeException("La especialidad no existe o está inactiva."));

			String correoAnterior = doctor.getEmail();
			mapper.updateEntity(dto, doctor, especialidad);

			DoctorEntity guardado = doctorRepo.saveAndFlush(doctor);
			if (correoAnterior == null || !correoAnterior.equalsIgnoreCase(dto.email().trim())) {
				try {
					authUserClient.actualizarCorreoDoctor(id, dto.email().trim());
				} catch (FeignException e) {
					throw new RuntimeException("No se pudo sincronizar el correo de acceso del doctor.", e);
				}
			}

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
            doctorRepo.saveAndFlush(doctor);
			try {
				authUserClient.actualizarEstadoDoctor(id, false);
			} catch (FeignException e) {
				throw new RuntimeException("No se pudo desactivar la cuenta de acceso del doctor.", e);
			}
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
            doctorRepo.saveAndFlush(doctor);
			try {
				authUserClient.actualizarEstadoDoctor(id, true);
			} catch (FeignException e) {
				throw new RuntimeException("No se pudo reactivar la cuenta de acceso del doctor.", e);
			}
            return true;
        }
        return false;
    }

}
