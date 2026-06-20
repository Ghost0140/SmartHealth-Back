package com.smarthealth.auth.feign;

public record DoctorFeign(
		 Integer idDoctor,
		 String nombres,
		 String apellidos,
		 Boolean activo
) {}
