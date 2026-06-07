package com.smarthealth.citas_service.feign;

import lombok.Data;

@Data
public class DoctorFeign {
	
	private Integer idDoctor;
	
	private String nombres;
	
	private String apellidos;
	
	private String especialidad;
	
	private String disponibilidad;
	
	private Boolean estado;

}
