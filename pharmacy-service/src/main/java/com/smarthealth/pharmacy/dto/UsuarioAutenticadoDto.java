package com.smarthealth.pharmacy.dto;

public record UsuarioAutenticadoDto(
	    Integer idUsuario,
	    String correo,
	    String rol,
	    Integer idDoctor
) {}
