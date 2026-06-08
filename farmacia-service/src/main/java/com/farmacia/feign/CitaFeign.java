package com.farmacia.feign;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CitaFeign {

    private Integer idCita;
    private Integer idPaciente;
    private Integer idDoctor;
    private LocalDateTime fechaCita;
    private String estado;
    private Boolean activo;
}