package com.smarthealth.citas_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "citas")
public class CitaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCita;

	private Integer idPaciente;

	private Integer idDoctor;

	private LocalDateTime fechaCita;

	@Enumerated(EnumType.STRING)
	private EstadoCita estado;

	private Boolean activo;

}
