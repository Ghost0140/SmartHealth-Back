package com.smarthealth.reception.dto;

public record UsuarioAutenticadoDto(
		Integer idUsuario,
	    String correo,
	    String rol,
	    Integer idDoctor
) {}
