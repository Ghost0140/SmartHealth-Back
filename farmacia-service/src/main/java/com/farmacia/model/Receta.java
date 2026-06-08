package com.farmacia.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recetas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReceta;

    private Integer idCita;
    private Integer idMedicamento;
    private String nombreMedicamento;
    private Integer cantidad;
    private LocalDate fechaReceta;
    private String estado;
}