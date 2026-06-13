package com.notificaciones.rabbitMQ;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaEvent {

    private Integer idCita;

    private Integer idPaciente;

    private Integer idDoctor;

    private LocalDateTime fechaCita;

    private EstadoCita estado;

    private Boolean activo;
}