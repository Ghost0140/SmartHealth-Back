package com.smarthealth.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarthealth.auth.dto.CambiarClaveDto;
import com.smarthealth.auth.dto.LoginRequestDto;
import com.smarthealth.auth.dto.LoginResponseDto;
import com.smarthealth.auth.dto.ResetClaveDto;
import com.smarthealth.auth.dto.UsuarioAutenticadoDto;
import com.smarthealth.auth.dto.UsuarioCreateDto;
import com.smarthealth.auth.dto.UsuarioResponseDto;
import com.smarthealth.auth.dto.UsuarioUpdateDto;
import com.smarthealth.auth.entity.RolEntity;
import com.smarthealth.auth.entity.UsuarioEntity;
import com.smarthealth.auth.feign.ClienteDoctorFeign;
import com.smarthealth.auth.feign.DoctorFeign;
import com.smarthealth.auth.mapper.UsuarioMapper;
import com.smarthealth.auth.repository.RolRepository;
import com.smarthealth.auth.repository.UsuarioRepository;
import com.smarthealth.auth.security.JwtUtil;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepo;

	private final RolRepository rolRepo;
	
	private final UsuarioMapper mapper;
	
	private final ClienteDoctorFeign clienteDoctor;

	private final BCryptPasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;
	
	@Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioResponseDto> listarUsuarios(Boolean activo) {
        List<UsuarioEntity> usuarios =
                activo == null
                        ? usuarioRepo.findAllWithRol()
                        : usuarioRepo.findByActivoWithRol(activo);

        return usuarios.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<UsuarioResponseDto> obtenerUsuarioPorId(Integer id) {
        return usuarioRepo.findByIdWithRol(id)
                .map(mapper::toResponseDto);
    }
	
	@Transactional(readOnly = true)
	public LoginResponseDto login(LoginRequestDto dto) {
	    UsuarioEntity usuario = usuarioRepo
	    		.findByCorreo(dto.correo())
	            .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

	    if (!usuario.getActivo()) {
	        throw new RuntimeException("El usuario está inactivo");
	    }
	    
		if ("DOCTOR".equals(usuario.getRol().getNombre()) && usuario.getIdDoctor() != null) {
			DoctorFeign doctor = clienteDoctor.obtenerDoctorPorId(usuario.getIdDoctor());

			if (!doctor.activo()) {
				throw new RuntimeException("El doctor asociado está inactivo");
			}
		}

	    if (!passwordEncoder.matches(dto.clave(), usuario.getClave())) {
	        throw new RuntimeException("Credenciales incorrectas");
	    }

	    String token = jwtUtil.generateToken(usuario);

	    return new LoginResponseDto(
	            token,
	            usuario.getIdUsuario(),
	            usuario.getCorreo(),
	            usuario.getRol().getNombre(),
	            usuario.getIdDoctor()
	    );
	}
	
	@Transactional
    @PreAuthorize("hasRole('ADMIN')")
	public UsuarioResponseDto registrarUsuario(UsuarioCreateDto dto) {
		if (usuarioRepo.existsByCorreo(dto.correo())) {
		    throw new RuntimeException("El correo ya está registrado");
		}
		
		RolEntity rol = rolRepo
				.findById(dto.idRol())
		        .orElseThrow(() -> new RuntimeException("El rol no existe"));
		
		if (!rol.getActivo()) {
		    throw new RuntimeException("El rol está inactivo");
		}
		
		if ("DOCTOR".equals(rol.getNombre())) {
			if (dto.idDoctor() == null) {
				throw new RuntimeException("Debe indicar el doctor asociado");
			}

		    if (usuarioRepo.existsByIdDoctor(dto.idDoctor())) {
		        throw new RuntimeException("El doctor ya tiene un usuario asociado");
		    }

		    try {
		        DoctorFeign doctor = clienteDoctor.obtenerDoctorPorId(dto.idDoctor());
				if (!doctor.activo()) {
					throw new RuntimeException("El doctor está inactivo");
				}
			} catch (FeignException.NotFound e) {
				throw new RuntimeException("El doctor no existe");
			} catch (FeignException e) {
				throw new RuntimeException("Error al consultar el servicio de doctores");
			}
		} else {
			if (dto.idDoctor() != null) {
				throw new RuntimeException("Solo un usuario con rol DOCTOR puede tener idDoctor");
			}
		}
		
		UsuarioEntity usuario = mapper.toEntity(dto, rol);
		
		usuario.setClave(passwordEncoder.encode(dto.clave()));
		
		UsuarioEntity guardado = usuarioRepo.save(usuario);
		
		return mapper.toResponseDto(guardado);
	}
	
	@Transactional
    @PreAuthorize("hasRole('ADMIN')")
	public Optional<UsuarioResponseDto> actualizarUsuario(Integer id, UsuarioUpdateDto dto) {
		return usuarioRepo.findByIdWithRol(id).map(usuario -> {
		    if (usuarioRepo.existsByCorreoAndIdUsuarioNot(dto.correo(), id)) {
		        throw new RuntimeException("El correo ya está registrado");
		    }

		    mapper.updateEntity(dto, usuario);

		    usuarioRepo.save(usuario);

		    return mapper.toResponseDto(usuario);
		});
	}
	
	@Transactional
	public void cambiarMiClave(CambiarClaveDto dto) {
	    UsuarioAutenticadoDto autenticado =
	            (UsuarioAutenticadoDto) SecurityContextHolder
	                    .getContext()
	                    .getAuthentication()
	                    .getPrincipal();

	    UsuarioEntity usuario = usuarioRepo
	    				.findByIdWithRol(autenticado.idUsuario())
	                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    if (!passwordEncoder.matches(
	            dto.claveActual(),
	            usuario.getClave())
	    ) {
	        throw new RuntimeException("La clave actual es incorrecta");
	    }

		usuario.setClave(passwordEncoder.encode(dto.nuevaClave()));

		usuarioRepo.save(usuario);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public void resetClave(Integer id, ResetClaveDto dto) {
		UsuarioEntity usuario = usuarioRepo
				.findByIdWithRol(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		usuario.setClave(passwordEncoder.encode(dto.nuevaClave()));

		usuarioRepo.save(usuario);
	}

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean reactivarUsuario(Integer id) {
		Optional<UsuarioEntity> existente = usuarioRepo.findByIdWithRol(id);
		if (existente.isPresent()) {
			UsuarioEntity usuario = existente.get();
			usuario.setActivo(true);
			usuarioRepo.save(usuario);
			return true;
		}
		return false;
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public boolean desactivarUsuario(Integer id) {
		Optional<UsuarioEntity> existente = usuarioRepo.findByIdWithRol(id);
		if (existente.isPresent()) {
			UsuarioEntity usuario = existente.get();
			usuario.setActivo(false);
			usuarioRepo.save(usuario);
			return true;
		}
		return false;
	}

}
