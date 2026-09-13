package com.historial.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "historial_clinico")
public class HistorialClinico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer idDoctor;
	private Integer idPaciente;
	private String diagnostico;
	private String tratamiento;
	private String observacion;
	private LocalDate fechaAtencion;
}