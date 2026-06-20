package com.smarthealth.auth.mapper;

import org.springframework.stereotype.Component;

import com.smarthealth.auth.dto.UsuarioCreateDto;
import com.smarthealth.auth.dto.UsuarioResponseDto;
import com.smarthealth.auth.dto.UsuarioUpdateDto;
import com.smarthealth.auth.entity.RolEntity;
import com.smarthealth.auth.entity.UsuarioEntity;

@Component
public class UsuarioMapper {
	
	public UsuarioEntity toEntity(UsuarioCreateDto dto, RolEntity rol) {
		UsuarioEntity usuario = new UsuarioEntity();

		usuario.setCorreo(dto.correo());
		usuario.setClave(dto.clave());
		usuario.setRol(rol);
		usuario.setIdDoctor(dto.idDoctor());
		usuario.setActivo(true);

		return usuario;
	}
	
	public UsuarioResponseDto toResponseDto(UsuarioEntity usuario) {
        return new UsuarioResponseDto(
        		usuario.getIdUsuario(),
        		usuario.getCorreo(),
        		usuario.getRol().getIdRol(),
        		usuario.getRol().getNombre(),
        		usuario.getIdDoctor(),
        		usuario.getActivo()
        );
    }
	
	public void updateEntity(
			UsuarioUpdateDto dto,
			UsuarioEntity usuario
	) {
		usuario.setCorreo(dto.correo());
	}

}
