package com.smarthealth.appointment.dto;

public record UsuarioAutenticadoDto(
	    Integer idUsuario,
	    String correo,
	    String rol,
	    Integer idDoctor
) {}
