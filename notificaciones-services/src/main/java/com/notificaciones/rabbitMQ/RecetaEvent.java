package com.notificaciones.rabbitMQ;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaEvent {

	private Integer idReceta;
    private Integer idCita;
    private Integer idMedicamento;
    private String nombreMedicamento;
    private Integer cantidad;
    private LocalDate fechaReceta;
    private String mensaje;
}