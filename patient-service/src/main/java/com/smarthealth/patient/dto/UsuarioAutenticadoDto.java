package com.smarthealth.patient.dto;

public record UsuarioAutenticadoDto(
	    Integer idUsuario,
	    String correo,
	    String rol,
	    Integer idDoctor
) {}
