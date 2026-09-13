package com.historial.feign;

public record DoctorFeign(
		 Integer idDoctor,
		 String nombres,
		 String apellidos,
		 Boolean activo
) {}
