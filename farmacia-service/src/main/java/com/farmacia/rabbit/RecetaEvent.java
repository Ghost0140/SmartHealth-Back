package com.farmacia.rabbit;

import java.time.LocalDate;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaEvent {

    private Integer idReceta;
    private Integer idCita;
    private Integer idPaciente;   
    private Integer idMedicamento;
    private String nombreMedicamento;
    private Integer cantidad;
    private LocalDate fechaReceta;
    private String mensaje;
}
