package com.farmacia.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class RegistroRecetaDTO {

    @NotNull(message = "El id de la cita es obligatorio")
    private Integer idCita;

    @NotNull(message = "El id del medicamento es obligatorio")
    private Integer idMedicamento;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
}