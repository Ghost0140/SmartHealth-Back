package com.smarthealth.citas_service.feign;

import lombok.Data;

@Data
public class PacienteFeign {

	private Integer idPaciente;

	private String nombres;

	private String apellidos;

	private String dni;

	private String telefono;

	private String email;

	private Boolean estado;

}
